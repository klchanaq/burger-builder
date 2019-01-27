package com.example.burgerbuilder.web.rest;

import com.example.burgerbuilder.domain.Customer;
import com.example.burgerbuilder.domain.CustomerOrder;
import com.example.burgerbuilder.domain.EmbeddedDomain.Ingredients;
import com.example.burgerbuilder.domain.enumeration.DELIVERYMETHOD_TYPES;
import com.example.burgerbuilder.repository.CustomerDAO;
import com.example.burgerbuilder.repository.CustomerOrderDAO;
import com.example.burgerbuilder.repository.CustomerOrderRepository;
import com.example.burgerbuilder.repository.CustomerRepository;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private EntityManager em;

    public TestResource(CustomerDAO customerDAO, CustomerOrderDAO customerOrderDAO, CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository) {
        this.customerDAO = customerDAO;
        this.customerOrderDAO = customerOrderDAO;
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    @GetMapping("/test/mergeOperationInternal")
    // @Transactional
    public void mergeOperationInternal() {
        Customer customer = new Customer();
        customer.setId(1L);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(1L);
        customerOrder.setDeliveryMethod(DELIVERYMETHOD_TYPES.FASTEST);
        customerOrder.setPrice(12.3f);
        customerOrder.setIngredients(new Ingredients());
        customerOrder.setCustomer(customer);
        CustomerOrder customerOrderMerged = customerOrderRepository.saveAndFlush(customerOrder);
        // em.unwrap(Session.class).getSessionFactory().getCache().evictAll();
        // em.unwrap(Session.class).clear();
        // customerOrderMerged.setPrice(13.3f);
        System.out.println("Thread.currentThread() = " + Thread.currentThread());
        CustomerOrder anotherCustomerOrder = customerOrderRepository.findById(2L).get();
        CustomerOrder customerOrderFound = customerOrderRepository.findById(1L).get();
        System.out.println("customerOrderFound = " + customerOrderFound);
        System.out.println("customerOrderMerged = " + customerOrderMerged);
        System.out.println("customerOrder == customerOrderMerged ? " + (customerOrder == customerOrderMerged));
        System.out.println("customerOrderMerged == customerOrderFound ? " + (customerOrderMerged == customerOrderFound));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CustomerOrder order = customerOrderRepository.findById(1L).get();
                System.out.println("(order == customerOrderMerged ? " + (order == customerOrderMerged));
                System.out.println("(order == customerOrderFound ? " + (order == customerOrderFound));
                //em.unwrap(Session.class);
                customerOrderRepository.findById(1L).get();
                //em.unwrap(Session.class);
                customerOrderRepository.findById(1L).get();
            }
        };
        new Thread(runnable).start();
    }

    @GetMapping("/test/multipleReadOperation1")
    //@Transactional
    public void multipleReadOperation1() throws InterruptedException {
        CustomerOrder customerOrderFound = customerOrderRepository.findById(1L).get();
        System.out.println("customerOrderFound = " + customerOrderFound);
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        Session session = em.unwrap(Session.class);
        System.out.println(session);
        System.out.println("session = " + session);
        SessionFactory sf = session.getSessionFactory();
        System.out.println("sf = " + sf);
        Statistics statistics = sf.getStatistics();
        System.out.println("statistics = " + statistics);
        Cache cache = sf.getCache();
        System.out.println("cache = " + cache.toString());
        System.out.println("statistics.getEntityNames() = " + Arrays.toString(statistics.getEntityNames()));

        //cache.evictAll();
        session.clear(); // this will clear the cache and make the second Query being executed.

        Thread.sleep(5000);
        CustomerOrder customerOrder = customerOrderRepository.findById(1L).get();
        System.out.println("(customerOrder == customerOrderFound) ? " + (customerOrder == customerOrderFound));
    }

    @GetMapping("/test/multipleReadOperation2")
    //@Transactional
    public void multipleReadOperation2() throws InterruptedException {
        CustomerOrder customerOrderFound = customerOrderRepository.findById(1L).get();
        System.out.println("customerOrderFound = " + customerOrderFound);
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        Session session = em.unwrap(Session.class);
        System.out.println(session);
        System.out.println("session = " + session);
        SessionFactory sf = session.getSessionFactory();
        System.out.println("sf = " + sf);
        Statistics statistics = sf.getStatistics();
        System.out.println("statistics = " + statistics);
        Cache cache = sf.getCache();
        System.out.println("cache = " + cache.toString());
        System.out.println("statistics.getEntityNames() = " + Arrays.toString(statistics.getEntityNames()));

        //cache.evictAll();
        session.clear(); // this will clear the cache and make the second Query being executed.

        Thread.sleep(5000);
        CustomerOrder customerOrder = customerOrderRepository.findById(1L).get();
        System.out.println("(customerOrder == customerOrderFound) ? " + (customerOrder == customerOrderFound));
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