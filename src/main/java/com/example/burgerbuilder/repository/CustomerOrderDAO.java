package com.example.burgerbuilder.repository;

import com.example.burgerbuilder.domain.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

@Repository
public class CustomerOrderDAO {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private EntityManager em;

    public CustomerOrderDAO() {
    }

    public CustomerOrderDAO(CustomerOrderRepository customerOrderRepository,
                            EntityManager em) {
        this.customerOrderRepository = customerOrderRepository;
        this.em = em;
    }

    public List<CustomerOrder> getCustomerOrdersByCustomerId(Long customerId) {
        TypedQuery<CustomerOrder> query = em.createQuery(
                "Select co From CustomerOrder co Where co.customer.id = :customerId",
                CustomerOrder.class);
        query.setParameter("customerId", customerId);
        List<CustomerOrder> customerOrderList = query.getResultList();
        return customerOrderList;
    }

    public List<CustomerOrder> getCustomerOdersByCustomerIds(Long[] customerIds) {
        TypedQuery<CustomerOrder> query = em.createQuery(
                "Select co From CustomerOrder co Where co.customer.id IN ( :customerIds )",
                CustomerOrder.class);
        query.setParameter("customerIds", Arrays.asList(customerIds));
        List<CustomerOrder> customerOrderList = query.getResultList();
        return customerOrderList;
    }

}