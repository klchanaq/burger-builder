package com.example.burgerbuilder.web.rest;

import com.example.burgerbuilder.domain.CustomerOrder;
import com.example.burgerbuilder.service.CustomerOrderService;
import com.example.burgerbuilder.web.rest.errors.BadRequestAlertException;
import com.example.burgerbuilder.web.rest.util.HeaderUtil;
import com.example.burgerbuilder.web.rest.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CustomerOrder.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = {"*"},
        allowedHeaders = {"*"},
        exposedHeaders =  {"X-burgerBuilder-alert", "X-burgerBuilder-params", "X-burgerBuilder-error"},
        maxAge = 3600)
public class CustomerOrderResource {

    private final Logger log = LoggerFactory.getLogger(CustomerOrderResource.class);

    private static final String ENTITY_NAME = "customerOrder";

    private final CustomerOrderService customerOrderService;

    public CustomerOrderResource(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    /**
     * POST  /customerOrders : Create a new customerOrder.
     *
     * @param customerOrder the customerOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerOrder, or with status 400 (Bad Request) if the customerOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customerOrders")
    public ResponseEntity<CustomerOrder> createCustomerOrder(@Valid @RequestBody CustomerOrder customerOrder) throws URISyntaxException {
        log.debug("REST request to save CustomerOrder : {}", customerOrder);
        if (customerOrder.getId() != null) {
            throw new BadRequestAlertException("A new customerOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Long actualCustomerId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        Long claimedCustomerId = customerOrder.getCustomer().getId();
        Assert.isTrue(actualCustomerId.equals(claimedCustomerId), "[Assertion failed] Ids do not match. User can only edit his / her data.");

        CustomerOrder result = customerOrderService.save(customerOrder);
        return ResponseEntity.created(new URI("/api/customerOrders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /customerOrder : Updates an existing customerOrder.
     *
     * @param customerOrder the customerOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerOrder,
     * or with status 400 (Bad Request) if the customerOrder is not valid,
     * or with status 500 (Internal Server Error) if the customerOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customerOrders")
    public ResponseEntity<CustomerOrder> updateCustomerOrder(@Valid @RequestBody CustomerOrder customerOrder) throws URISyntaxException {
        log.debug("REST request to update CustomerOrder : {}", customerOrder);
        if (customerOrder.getId() == null) {
            return createCustomerOrder(customerOrder);
        }
        CustomerOrder result = customerOrderService.save(customerOrder);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerOrder.getId().toString()))
                .body(result);
    }

    /**
     * GET  /customerOrders : get all the customerOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerOrders in body
     */
    @GetMapping("/customerOrders")
    public List<CustomerOrder> getAllCustomerOrders() {
        log.debug("REST request to get all CustomerOrders");
        return customerOrderService.findAll();
    }

    /**
     * GET  /customerOrders/:id : get the "id" customerOrder.
     *
     * @param id the id of the customerOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerOrder, or with status 404 (Not Found)
     */
    @GetMapping("/customerOrders/{id}")
    public ResponseEntity<CustomerOrder> getCustomerOrder(@PathVariable Long id) {
        log.debug("REST request to get CustomerOrder : {}", id);
        Optional<CustomerOrder> customerOrder = customerOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerOrder);
    }

    /**
     * DELETE  /customerOrders/:id : delete the "id" customerOrder.
     *
     * @param id the id of the customerOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customerOrders/{id}")
    public ResponseEntity<Void> deleteCustomerOrder(@PathVariable Long id) {
        log.debug("REST request to delete CustomerOrder : {}", id);
        customerOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /customerOrders?customerId=:customerId : get all the CustomerOrders, filtered by customerId
     *
     * @param customerId the id of the Customer entity
     * @return the ResponseEntity with status 200 (OK) and the list of customerOrders in body
     */
    @GetMapping(value = "/customerOrders", params = {"customerId"})
    // Spring Security Annotations Examples
    // @PreAuthorize("prinicipal.username.equals(#customerId)") // this requires the change on @RequestParam Type from Long to String
    // @PreAuthorize("principal.username.equals(#customerId.toString())") // this is okay.
    // @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") // this is okay
    public List<CustomerOrder> getCustomerOrdersByCustomerId(
            @RequestParam("customerId") Long customerId,
            Principal principal,
            @AuthenticationPrincipal UserDetails authPrincipal) {
        System.out.println("principal = " + principal);
        System.out.println("principal.getClass() = " + principal.getClass());
        System.out.println("authPrincipal = " + authPrincipal);
        System.out.println("authPrincipal.getClass() = " + authPrincipal.getClass());

        String customerIdFromAuthentication = principal.getName();
        Long customerIdFromAuthenticationInLong = Long.valueOf(customerIdFromAuthentication);
        System.out.println("customerId = " + customerId);
        System.out.println("customerIdFromAuthenticationInLong = " + customerIdFromAuthenticationInLong);
        if (!customerIdFromAuthenticationInLong.equals(customerId)) {
            throw new BadRequestAlertException(
                    "Parameter CustomerId " + customerId +
                            " does not match " +
                            "Current User Id " + customerIdFromAuthenticationInLong, ENTITY_NAME, null);
        }
        log.debug("REST request to get CustomerOrders by customerId: {}", customerId);
        return customerOrderService.findCustomerOrdersByCustomerId(customerId);
    }

}