package com.example.burgerbuilder.web.rest;

import com.example.burgerbuilder.domain.Customer;
import com.example.burgerbuilder.service.CustomerService;
import com.example.burgerbuilder.service.dto.SimpleUserDTO;
import com.example.burgerbuilder.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing User Account.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = {"*"},
        allowedHeaders = {"*"},
        exposedHeaders = {"X-burgerBuilder-alert", "X-burgerBuilder-params", "X-burgerBuilder-error"},
        maxAge = 3600)
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private static final String ENTITY_NAME = "customer";

    private final CustomerService customerService;

    public AccountResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param simpleUserDTO the managed user View Model
     * @return the ResponseEntity with status 201 (Created) and with body the new customerOrder, or with status 400 (Bad Request) if the customerOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/register")
    public ResponseEntity<Customer> registerAccount(@Valid @RequestBody SimpleUserDTO simpleUserDTO) throws URISyntaxException {
        Customer customer = new Customer();
        customer.email(simpleUserDTO.getEmail())
                .password(simpleUserDTO.getPassword());
        Customer result = customerService.save(customer);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
