package com.example.burgerbuilder.repository;

import com.example.burgerbuilder.domain.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /* Without @EntityGraph will load the authorities lazily when accessing getAuthorities().... => authority.getName();
    *  2 Select Statements, no join
    *  */
    Optional<Customer> findOneByEmailIgnoreCase(String email);

    /* With @EntityGraph will load the authorities eagerly
    *  1 Select Statement, left outer join
    *  */
    @EntityGraph(attributePaths = "authorities")
    Optional<Customer> findOneWithAuthoritiesByEmailIgnoreCase(String email);

}