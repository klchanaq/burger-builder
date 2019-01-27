package com.example.burgerbuilder.domain;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Customer.class)
public class Customer implements Serializable {

    private static final long serialVersionUID = 27821924L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Email
    @Size(min = 5, max = 254)
    @Column(nullable = false, length = 254, unique = true)
    private String email;

    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "customer_authority",
            joinColumns = {@JoinColumn(name = "customer_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    //@JsonManagedReference
    //@JsonBackReference
    private Set<CustomerOrder> customerOrders = new HashSet<>();

    @UpdateTimestamp
    private LocalDate updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Customer password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public Customer authorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Customer addAuthorities(Authority authority) {
        this.authorities.add(authority);
        return this;
    }

    public Customer removeAuthorities(Authority authority) {
        this.authorities.remove(authority);
        return this;
    }

    public Set<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public Customer customerOrders(Set<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
        return this;
    }

    public void setCustomerOrders(Set<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public Customer addCustomerOrders(CustomerOrder customerOrder) {
        this.customerOrders.add(customerOrder);
        customerOrder.setCustomer(this);
        return this;
    }

    public Customer removeCustomerOrders(CustomerOrder customerOrder) {
        this.customerOrders.remove(customerOrder);
        customerOrder.setCustomer(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer that = (Customer) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                // ", customerOrders=" + customerOrders +
                '}';
    }
}