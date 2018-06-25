package com.example.burgerbuilder.config;

import com.example.burgerbuilder.domain.Author;
import com.example.burgerbuilder.service.AuthorService;
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
    CommandLineRunner commandLineRunner(AuthorService authorService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Author author = new Author();
                author.setName("John White");
                author.setAge(20);
                author.setRegisterDate(LocalDate.now());
                author.setLastSignDateTime(ZonedDateTime.now());
                authorService.save(author);
                Author author2 = new Author();
                author2.setName("Mary Green");
                author2.setAge(30);
                author2.setRegisterDate(LocalDate.now());
                author2.setLastSignDateTime(ZonedDateTime.now());
                authorService.save(author2);
            }
        };
    }

}
