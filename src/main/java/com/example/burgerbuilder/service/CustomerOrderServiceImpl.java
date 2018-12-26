package com.example.burgerbuilder.service;

import com.example.burgerbuilder.domain.CustomerOrder;
import com.example.burgerbuilder.repository.CustomerOrderDAO;
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

    private final CustomerOrderRepository customerOrderRepository;

    private final CustomerOrderDAO customerOrderDAO;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository, CustomerOrderDAO customerOrderDAO) {
        this.customerOrderRepository = customerOrderRepository;
        this.customerOrderDAO = customerOrderDAO;
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
        return customerOrderRepository.save(CustomerOrder);
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
        return customerOrderRepository.findAll();
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
        return customerOrderRepository.findById(id);
    }

    /**
     * Delete the "id" CustomerOrder.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to get delete an CustomerOrder : {}", id);
        customerOrderRepository.deleteById(id);
    }

    /**
     * Get all the CustomerOrders, filtered by customerId
     *
     * @param customerId the id of the Customer entity
     * @return the collection of entities which match the condition
     */
    @Override
    public List<CustomerOrder> findCustomerOrdersByCustomerId(Long customerId) {
        log.debug("Request to get find CustomerOrders by customerId: {}", customerId);
        return customerOrderRepository.findByCustomer_Id(customerId);
    }

}