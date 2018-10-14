package com.example.burgerbuilder.service;

import com.example.burgerbuilder.domain.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Customer.
 */
public interface CustomerService {

    /**
     * Save a customer.
     *
     * @param customer the entity to save
     * @return the persisted entity
     */
    Customer save(Customer customer);

    /**
     * Save a customer.
     *
     * @param customer the demo entity to save
     * @return the persisted entity
     */
    Customer saveWithDemoData(Customer customer);

    /**
     * Get all the customers.
     *
     * @return the collection of entities
     */
    List<Customer> findAll();

    /**
     * Get the "id" customer.
     *
     * @param id the id of the entity
     * @return the optional entity
     */
    Optional<Customer> findOne(Long id);

    /**
     * Delete the "id" customer.
     *
     * @param id the entity id to delete
     */
    void delete(Long id);

}
