package com.example.burgerbuilder.domain;

import com.example.burgerbuilder.domain.EmbeddedDomain.Ingredients;
import com.example.burgerbuilder.domain.enumeration.DELIVERYMETHOD_TYPES;
import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/*
 * Reminder 1: Order is a Reserved Word on RDBMS SQL, Do not try to use the domain name "Order"
 * Reminder 2: @Embeddable & @Embedded are a pair of annotations for Inner Object,
 *             @Embedded can be used at methods (getters) or field
 * Reminder 3: { @Min, @Max } (for Numeric Types),
 *             @Size(min = 0, max = 30) (for String, Map, Set and other Object Types),
 *             @NotNull, @Nullable
 *             are useless if there is no "validation-api" module ( included on Hibernate alread )
 *             which means they are effective when using Hibernate's provided functions such as dealing with Database e.g. CRUD
 *             CustomerOrder customerOrder = new CustomerOrder(); // OK
 *             customerOrder.customerName(null).ingredients(new Ingredients(1,2,1,3)) // OK
 *             customerOrderService.save(customerOrder) // Error!
 *             error because on the preInsert() method provided by Hibernate, it will call validate() method to check the customerOrder object's fields
 * Reminder 4: If you mark @Id & @GeneratedValue on Fields,
 *             Hibernate will not use the setters, getters, default constructor to do its jobs.
 *             If you mark @Id & @GeneratedValue on getters, Hibernate will use the setters, getters, default constructor to do its jobs.
 *             Please refer to another project regarding Hibernate, JPA & Jackson.
 * */

@Entity
@Table(name = "customerOrder")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = CustomerOrder.class)
public class CustomerOrder implements Serializable {

    private static final long serialVersionUID = 1234L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Embedded
    private Ingredients ingredients = new Ingredients();

    @NotNull
    // For Float, Double ( v.s. @Min, @Max for Short, Integer, Long Learned from Jhipster on 18-07-2018
    // Actually both @Min and @DecimalMin are the same, and both @Max and @DecimalMax are nearly the same but for intuitive behavior
    // Learned from Jhipster on 18-07-2018
    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    //@Min(0)
    //@Max(20)
    @Column(nullable = false)
    private Float price;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

    @NotNull
    // Learned from Jhipster on 18-07-2018, For passing String (e.g. deliveryMetod: "FASTEST") at frontend to @RequestBody at Spring
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DELIVERYMETHOD_TYPES deliveryMethod = DELIVERYMETHOD_TYPES.NOT_SPECIFIC; // Don't use Enum<T> here!

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    //cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties(value = {"customerOrders"}) // Learned from Jhipster on 18-07-2018, or value = "" for general purpose
    //@JsonBackReference
    //@JsonManagedReference
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public CustomerOrder ingredients(Ingredients ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerOrder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Float getPrice() {
        return price;
    }

    public CustomerOrder price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public DELIVERYMETHOD_TYPES getDeliveryMethod() {
        return deliveryMethod;
    }

    public CustomerOrder deliveryMethod(DELIVERYMETHOD_TYPES deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
        return this;
    }

    public void setDeliveryMethod(DELIVERYMETHOD_TYPES deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrder that = (CustomerOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", ingredients=" + ingredients +
                ", price=" + price +
                ", deliveryMethod=" + deliveryMethod +
                // ", customer=" + customer +
                '}';
    }
}