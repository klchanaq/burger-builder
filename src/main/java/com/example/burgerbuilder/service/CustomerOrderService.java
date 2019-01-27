package com.example.burgerbuilder.service;

import com.example.burgerbuilder.domain.CustomerOrder;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CustomerOrder.
 */
public interface CustomerOrderService {

    /**
     * Save a CustomerOrder.
     *
     * @param customerOrder the entity to save
     * @return the persisted entity
     */
    CustomerOrder save(CustomerOrder customerOrder);

    /**
     * Save and Flush a CustomerOrder.
     *
     * @param customerOrder the entity to save
     * @return the persisted entity
     */
    CustomerOrder saveAndFlush(CustomerOrder customerOrder);

    /**
     * Get all the CustomerOrders.
     *
     * @return the collection of entities
     */
    List<CustomerOrder> findAll();

    /**
     * Get the "id" CustomerOrder.
     *
     * @param id the id of the entity
     * @return the optional entity
     */
    Optional<CustomerOrder> findOne(Long id);

    /**
     * Delete the "id" CustomerOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get all the CustomerOrders, filtered by customerId
     *
     * @param customerId the id of the Customer entity
     * @return the collection of entities which match the condition
     */
    List<CustomerOrder> findCustomerOrdersByCustomerId(Long customerId);

}