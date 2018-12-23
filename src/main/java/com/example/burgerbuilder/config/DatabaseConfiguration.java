package com.example.burgerbuilder.config;

import com.example.burgerbuilder.domain.Authority;
import com.example.burgerbuilder.domain.Customer;
import com.example.burgerbuilder.domain.CustomerOrder;
import com.example.burgerbuilder.domain.EmbeddedDomain.Address;
import com.example.burgerbuilder.domain.EmbeddedDomain.Ingredients;
import com.example.burgerbuilder.domain.enumeration.DELIVERYMETHOD_TYPES;
import com.example.burgerbuilder.repository.AuthorityRepository;
import com.example.burgerbuilder.security.AuthoritiesConstants;
import com.example.burgerbuilder.service.AuthorService;
import com.example.burgerbuilder.service.CustomerOrderService;
import com.example.burgerbuilder.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;

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
                                        CustomerService customerService,
                                        AuthorityRepository authorityRepository,
                                        @Qualifier(value = "spring4PasswordEncoder") PasswordEncoder passwordEncoder,
                                        CustomerOrderService customerOrderService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                Authority role_admin = new Authority();
                role_admin.setName(AuthoritiesConstants.ADMIN);

                Authority role_user = new Authority();
                role_user.setName(AuthoritiesConstants.USER);

                authorityRepository.save(role_admin);
                authorityRepository.saveAndFlush(role_user);

                Customer bob = new Customer();
                Address bobAddress = new Address();
                bobAddress.country("HK").street("HK Street");
                bob.email("bob@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .address(bobAddress);

                bob.addAuthorities(role_admin).addAuthorities(role_user);

                CustomerOrder bobOrder1 = new CustomerOrder();
                bobOrder1
                        .ingredients(new Ingredients(1, 2, 1, 3))
                        .price(9.5f)
                        .customer(bob);

                CustomerOrder bobOrder2 = new CustomerOrder();
                bobOrder2
                        .ingredients(new Ingredients(0, 0, 0, 5))
                        .price(11.5f)
                        .deliveryMethod(DELIVERYMETHOD_TYPES.FASTEST)
                        .customer(bob);

                // bob.addCustomerOrders(bobOrder1).addCustomerOrders(bobOrder2);
                customerService.saveWithDemoData(bob);
                customerOrderService.save(bobOrder1);
                customerOrderService.save(bobOrder2);

                Customer sara = new Customer();
                Address saraAddress = new Address();
                saraAddress.country("Germany").street("Germany Street");
                sara
                        .email("sara@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .address(saraAddress);

                sara.addAuthorities(role_user);

                CustomerOrder saraOrder1 = new CustomerOrder();
                saraOrder1
                        .ingredients(new Ingredients(5, 0, 1, 0))
                        .price(new Float(10.4f))
                        .deliveryMethod(DELIVERYMETHOD_TYPES.NORMAL)
                        .customer(sara);

                CustomerOrder saraOrder2 = new CustomerOrder();
                saraOrder2
                        .ingredients(new Ingredients(0, 2, 3, 0))
                        .price(7.5f)
                        .customer(sara);

                // sara.addCustomerOrders(saraOrder1).addCustomerOrders(saraOrder2);
                customerService.saveWithDemoData(sara);
                customerOrderService.save(saraOrder1);
                customerOrderService.save(saraOrder2);

            }
        };
    }

}