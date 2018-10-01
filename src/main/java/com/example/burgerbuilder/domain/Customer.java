package com.example.burgerbuilder.domain;

import com.example.burgerbuilder.domain.EmbeddedDomain.Address;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @Size(min = 0, max = 30)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Email
    @Size(min = 5, max = 254)
    @Column(nullable = false, length = 254, unique = true)
    private String email;

    @NotNull
    @Embedded
    private Address address;

    @UpdateTimestamp
    private LocalDate updatedDate;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    //@JsonManagedReference
    //@JsonBackReference
    private Set<CustomerOrder> customerOrders = new HashSet<>();

    public Customer() {
    }

    public Customer(Long id,
                    @NotNull @Size(min = 0, max = 30) String name,
                    @NotNull @Email @Size(min = 5, max = 254) String email,
                    @NotNull Address address,
                    Set<CustomerOrder> customerOrders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.customerOrders = customerOrders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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

    public Address getAddress() {
        return address;
    }

    public Customer address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
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
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                // ", customerOrders=" + customerOrders +
                '}';
    }
}