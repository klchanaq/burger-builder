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
     * @param CustomerOrder the entity to save
     * @return the persisted entity
     */
    CustomerOrder save(CustomerOrder CustomerOrder);

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
}