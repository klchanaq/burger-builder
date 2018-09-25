package com.example.burgerbuilder.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomerDAO {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager em;

    public CustomerDAO() {}

    public CustomerDAO(CustomerRepository customerRepository,
                       EntityManager em) {
        this.customerRepository = customerRepository;
        this.em = em;
    }

}