package com.example.burgerbuilder.config;

import com.example.burgerbuilder.domain.Author;
import com.example.burgerbuilder.domain.Customer;
import com.example.burgerbuilder.domain.CustomerOrder;
import com.example.burgerbuilder.domain.EmbeddedDomain.Address;
import com.example.burgerbuilder.domain.EmbeddedDomain.Ingredients;
import com.example.burgerbuilder.domain.enumeration.DELIVERYMETHOD_TYPES;
import com.example.burgerbuilder.service.AuthorService;
import com.example.burgerbuilder.service.CustomerOrderService;
import com.example.burgerbuilder.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Configuration
@EnableJpaRepositories("com.example.burgerbuilder.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    CommandLineRunner commandLineRunner(AuthorService authorService,
                                        CustomerOrderService customerOrderService,
                                        CustomerService customerService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Author author = new Author(
                        null,
                        "John White", 20,
                        LocalDate.of(2011, 10, 1), ZonedDateTime.now()
                );
                authorService.save(author);
                Author author2 = new Author();
                author2.setName("Mary Green");
                author2.setAge(30);
                author2.setRegisterDate(LocalDate.parse("2016-08-12"));
                author2.setLastSignDateTime(ZonedDateTime.now());
                authorService.save(author2);

                System.out.println("LocalDate.now() = " + LocalDate.now());
                System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
                System.out.println("ZonedDateTime.now() = " + ZonedDateTime.now());
                System.out.println("Instant.now() = " + Instant.now());

                Customer hkcustomer = new Customer();
                hkcustomer.setCustomerOrders(new ArrayList<>());
                Address hkcustomerAddress = new Address();
                hkcustomerAddress.country("HK").street("HK Street");
                CustomerOrder hkcustomerOrder1 = new CustomerOrder();
                hkcustomerOrder1
                        .ingredients(new Ingredients(1, 2, 1, 3))
                        .price(9.5f)
                        .customer(hkcustomer);

                CustomerOrder hkcustomerOrder2 = new CustomerOrder();
                hkcustomerOrder2
                        .ingredients(new Ingredients(0, 0, 0, 5))
                        .price(11.5f)
                        .deliveryMethod(DELIVERYMETHOD_TYPES.FASTEST)
                        .customer(hkcustomer);
                hkcustomer.name("hkcustomer").email("hkcustomer@gmail.com").address(hkcustomerAddress);
                //hkcustomer.setCustomerOrders(Arrays.asList(hkcustomerOrder1, hkcustomerOrder2));
                hkcustomer.addCustomerOrders(hkcustomerOrder1).addCustomerOrders(hkcustomerOrder2);
                customerService.save(hkcustomer);

                Customer germanCustomer = new Customer();
                germanCustomer.setCustomerOrders(new ArrayList<>());
                Address germanCustomerAddress = new Address();
                germanCustomerAddress.country("Germany").street("Germany Street");
                germanCustomer.name("germanCustomer").email("germanCustomer@gmail.com").address(germanCustomerAddress);

                CustomerOrder germanCustomerOrder1 = new CustomerOrder();
                germanCustomerOrder1
                        .ingredients(new Ingredients(5, 0, 1, 0))
                        .price(new Float(10.4f))
                        .deliveryMethod(DELIVERYMETHOD_TYPES.NORMAL)
                        .customer(germanCustomer);

                CustomerOrder germanCustomerOrder2 = new CustomerOrder();
                germanCustomerOrder2
                        .ingredients(new Ingredients(0, 2, 3, 0))
                        .price(7.5f)
                        .customer(germanCustomer);

                germanCustomer.addCustomerOrders(germanCustomerOrder1).addCustomerOrders(germanCustomerOrder2);
                customerService.save(germanCustomer);
                // customerOrderService.save(germanCustomerOrder1);
                // customerOrderService.save(germanCustomerOrder2);
            }
        };
    }

}