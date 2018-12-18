package com.example.burgerbuilder.web.rest;

import com.example.burgerbuilder.domain.Customer;
import com.example.burgerbuilder.security.jwt.JWTFilter;
import com.example.burgerbuilder.security.jwt.TokenProvider;
import com.example.burgerbuilder.service.CustomerService;
import com.example.burgerbuilder.service.dto.SimpleUserDTO;
import com.example.burgerbuilder.web.rest.util.HeaderUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/* Notes:
 * 1. For any class which can be serialized/deserialized by Jackson, it should possess setters and getters,
 *    and more importantly, those setters/getters must either be public or marked with Jackson annotations such @JsonProperty,
 *    otherwise, exception will be thrown regarding converter serialization problem
 * */

/**
 * REST controller for managing User Account.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = {"*"},
        allowedHeaders = {"*"},
        exposedHeaders = {"X-burgerBuilder-alert", "X-burgerBuilder-params", "X-burgerBuilder-error"},
        maxAge = 3600)
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private static final String ENTITY_NAME = "customer";

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final CustomerService customerService;

    public AccountResource(TokenProvider tokenProvider, AuthenticationManager authenticationManager, CustomerService customerService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param simpleUserDTO the managed user View Model
     * @return the ResponseEntity with status 201 (Created) and with body the new customer
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/register")
    public ResponseEntity<Customer> register(@Valid @RequestBody SimpleUserDTO simpleUserDTO) throws URISyntaxException {
        log.debug("Request to register new user account : {}", simpleUserDTO);
        Customer customer = new Customer();
        customer.email(simpleUserDTO.getEmail())
                .password(simpleUserDTO.getPassword());
        Customer result = customerService.save(customer);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    /**
     * @param simpleUserDTO the managed user View Model
     * @return the ResponseEntity with status ok and with body the new JWTToken
     */
    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authenticate(@Valid @RequestBody SimpleUserDTO simpleUserDTO) {
        log.debug("Request to authenticate user account : {}", simpleUserDTO);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(simpleUserDTO.getEmail(), simpleUserDTO.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = this.tokenProvider.createToken(authentication, false);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        JWTToken jwtToken = new JWTToken();

        String localId = authentication.getPrincipal() instanceof UserDetails ?
                ((UserDetails) authentication.getPrincipal()).getUsername() :
                authentication.getPrincipal() instanceof String ?
                (String) authentication.getPrincipal() : null;

        jwtToken
                .idToken(jwt)
                .localId(Long.valueOf(localId))
                .email(simpleUserDTO.getEmail());

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(jwtToken);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken implements Serializable {

        private static final long serialVersionUID = 9684L;

        @JsonProperty("idToken")
        private String idToken;

        @JsonProperty("localId")
        private Long localId;

        @JsonProperty("email")
        private String email;

        @JsonProperty("refreshToken")
        private String refreshToken = "to_be_set";

        String getIdToken() {
            return idToken;
        }

        JWTToken idToken(String idToken) {
            this.idToken = idToken;
            return this;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        Long getLocalId() {
            return localId;
        }

        JWTToken localId(Long localId) {
            this.localId = localId;
            return this;
        }

        void setLocalId(Long localId) {
            this.localId = localId;
        }

        String getEmail() {
            return email;
        }

        JWTToken email(String email) {
            this.email = email;
            return this;
        }

        void setEmail(String email) {
            this.email = email;
        }

        String getRefreshToken() {
            return refreshToken;
        }

        JWTToken refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            JWTToken jwtToken = (JWTToken) o;
            return Objects.equals(email, jwtToken.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(email);
        }

        @Override
        public String toString() {
            return "JWTToken{" +
                    "idToken='" + idToken + '\'' +
                    ", localId=" + localId +
                    ", email='" + email + '\'' +
                    ", refreshToken='" + refreshToken + '\'' +
                    '}';
        }
    }

}
