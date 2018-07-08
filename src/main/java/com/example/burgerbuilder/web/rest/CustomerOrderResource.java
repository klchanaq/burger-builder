package com.example.burgerbuilder.web.rest;

import com.example.burgerbuilder.domain.CustomerOrder;
import com.example.burgerbuilder.service.CustomerOrderService;
import com.example.burgerbuilder.web.rest.errors.BadRequestAlertException;
import com.example.burgerbuilder.web.rest.util.HeaderUtil;
import com.example.burgerbuilder.web.rest.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CustomerOrder.
 */
@RestController
@RequestMapping("/api")
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

}
