package com.example.burgerbuilder.repository;

import com.example.burgerbuilder.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerDAO {

    @Autowired
    private CustomerRepository customerRepository;

    @PersistenceContext
    private EntityManager em;

    public CustomerDAO() {}

    public CustomerDAO(CustomerRepository customerRepository,
                       EntityManager em) {
        this.customerRepository = customerRepository;
        this.em = em;
    }

    public Customer persist(Customer customer) {
        em.persist(customer);
        em.flush();
        return customer;
    }

    public Customer merge(Customer customer) {
        return em.merge(customer);
    }

}