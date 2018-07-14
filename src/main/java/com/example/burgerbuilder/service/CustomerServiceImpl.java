package com.example.burgerbuilder.service;

import com.example.burgerbuilder.domain.Customer;
import com.example.burgerbuilder.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save
     * @return the persisted entity
     */
    @Override
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Get all the customers.
     *
     * @return the collection of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        log.debug("Request to get all Customers");
        List<Customer> customers = customerRepository.findAll();
        // customers.stream().forEach(customer -> System.out.println("customer.getCustomerOrders() = " + customer.getCustomerOrders()));
        return customers;
    }

    /**
     * Get the "id" customer.
     *
     * @param id the id of the entity
     * @return the optional entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findOne(Long id) {
        log.debug("Request to get find an Customer id: {}", id);
        return customerRepository.findById(id);
    }

    /**
     * Delete the "id" customer.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to get delete an Customer : {}", id);
        customerRepository.deleteById(id);
    }
}