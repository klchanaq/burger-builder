package com.example.burgerbuilder.service;

import com.example.burgerbuilder.domain.CustomerOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.burgerbuilder.repository.CustomerOrderRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing CustomerOrder.
 */
@Service
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final Logger log = LoggerFactory.getLogger(CustomerOrderServiceImpl.class);

    private final CustomerOrderRepository CustomerOrderRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository CustomerOrderRepository) {
        this.CustomerOrderRepository = CustomerOrderRepository;
    }

    /**
     * Save a CustomerOrder.
     *
     * @param CustomerOrder the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerOrder save(CustomerOrder CustomerOrder) {
        log.debug("Request to save CustomerOrder : {}", CustomerOrder);
        return CustomerOrderRepository.save(CustomerOrder);
    }

    /**
     * Get all the CustomerOrders.
     *
     * @return the collection of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerOrder> findAll() {
        log.debug("Request to get all CustomerOrders");
        return CustomerOrderRepository.findAll();
    }

    /**
     * Get the "id" CustomerOrder.
     *
     * @param id the id of the entity
     * @return the optional entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerOrder> findOne(Long id) {
        log.debug("Request to get find an CustomerOrder id: {}", id);
        return CustomerOrderRepository.findById(id);
    }

    /**
     * Delete the "id" CustomerOrder.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to get delete an CustomerOrder : {}", id);
        CustomerOrderRepository.deleteById(id);
    }

}
