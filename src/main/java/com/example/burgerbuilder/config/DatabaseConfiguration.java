package com.example.burgerbuilder.config;

import com.example.burgerbuilder.domain.Author;
import com.example.burgerbuilder.domain.CustomerOrder;
import com.example.burgerbuilder.repository.Ingredients;
import com.example.burgerbuilder.service.AuthorService;
import com.example.burgerbuilder.service.CustomerOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.ZonedDateTime;

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
    CommandLineRunner commandLineRunner(AuthorService authorService, CustomerOrderService customerOrderService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Author author = new Author(
                        null,
                        "John White", 20,
                        LocalDate.now(), ZonedDateTime.now()
                );
                authorService.save(author);
                Author author2 = new Author();
                author2.setName("Mary Green");
                author2.setAge(30);
                author2.setRegisterDate(LocalDate.now());
                author2.setLastSignDateTime(ZonedDateTime.now());
                authorService.save(author2);

                CustomerOrder customerOrder1 = new CustomerOrder(
                        null, "Maxi", new Ingredients()
                );
                customerOrderService.save(customerOrder1);

                CustomerOrder customerOrder2 = new CustomerOrder(
                        null, "Jane", new Ingredients(1,2,1,3)
                );
                customerOrderService.save(customerOrder2);
            }
        };
    }

}
