package com.example.burgerbuilder.web.rest;

import com.example.burgerbuilder.domain.Customer;
import com.example.burgerbuilder.domain.CustomerOrder;
import com.example.burgerbuilder.repository.CustomerDAO;
import com.example.burgerbuilder.repository.CustomerOrderDAO;
import com.example.burgerbuilder.repository.CustomerOrderRepository;
import com.example.burgerbuilder.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = {"*"},
        allowedHeaders = {"*"},
        exposedHeaders = {"X-burgerBuilder-alert", "X-burgerBuilder-params", "X-burgerBuilder-error"},
        maxAge = 3600)
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CustomerDAO customerDAO;

    private final CustomerOrderDAO customerOrderDAO;

    private final CustomerRepository customerRepository;

    private final CustomerOrderRepository customerOrderRepository;

    public TestResource(CustomerDAO customerDAO, CustomerOrderDAO customerOrderDAO, CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository) {
        this.customerDAO = customerDAO;
        this.customerOrderDAO = customerOrderDAO;
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    @GetMapping("/test/getCustomerOrdersByCustomerId")
    public List<CustomerOrder> getCustomerOrdersByCustomerId(@RequestParam Long customerId) {
        log.info("Rest Request getCustomerOrdersByCustomerId, CustomerId -> {}", customerId);
        // List<CustomerOrder> customerOrdersByCustomerId = customerOrderDAO.getCustomerOrdersByCustomerId(customerId);
        return customerOrderRepository.findByCustomer_Id(customerId);
    }

    @GetMapping("/test/getCustomerOrdersByCustomerIds")
    public List<CustomerOrder> getCustomerOrdersByCustomerIds(@RequestParam Long[] customerIds) {
        log.info("Rest Request getCustomerOrdersByCustomerIds, CustomerIds -> {}", customerIds);
        // List<CustomerOrder> customerOrdersByCustomerIds = customerOrderDAO.getCustomerOdersByCustomerIds(customerIds);
        // return customerOrderRepository.findByCustomer_IdIsIn(Arrays.asList(customerIds));
        return customerOrderRepository.findAll(new Specification<CustomerOrder>() {
            @Override
            public Predicate toPredicate(Root<CustomerOrder> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                Path<Customer> customer = root.get("customer");
                Predicate customerIdsIn = customer.get("id").in(Arrays.asList(customerIds));
                CriteriaQuery<?> where = query.where(customerIdsIn);
                return customerIdsIn;
            }
        });
    }

}