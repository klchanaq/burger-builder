package com.example.burgerbuilder.service;

import com.example.burgerbuilder.domain.Customer;
import com.example.burgerbuilder.repository.CustomerRepository;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final CustomerRepository customerRepository;

    public DomainUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Authenticating {}", email);
        if (new EmailValidator().isValid(email, null)) {
            return customerRepository.findOneWithAuthoritiesByEmailIgnoreCase(email)
                    .map(customer -> createSpringSecurityUser(customer))
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User with email " + email + " was not found in the database"));
        }
        throw new UsernameNotFoundException("Email is invalid");
    }

    private User createSpringSecurityUser(Customer customer) {
        List<GrantedAuthority> grantedAuthorities =
                customer.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                        .collect(Collectors.toList());
        return new User(customer.getEmail(), customer.getPassword(), grantedAuthorities);
    }
}
