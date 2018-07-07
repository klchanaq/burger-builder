package com.example.burgerbuilder.domain;

import com.example.burgerbuilder.repository.Ingredients;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/*
* Reminder 1: Order is a Reserved Word on RDBMS SQL, Do not try to use the domain name "Order"
* Reminder 2: @Embeddable & @Embedded are a pair of annotations for Inner Object
* Reminder 3: @NotNull, @Nullable are only for Persistence Layer,
*               which means they are only effective when dealing with Database e.g. CRUD
*               CustomerOrder customerOrder = new CustomerOrder(); // OK
*               customerOrder.customerName(null).ingredients(new Ingredients(1,2,1,3)) // OK
*               customerOrderService.save(customerOrder) // Error!
* */

@Entity
@Table(name = "customerOrder")
public class CustomerOrder implements Serializable {

    private static final long serialVersionUID = 1234L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Max(value = 20)
    private String customerName;

    @NotNull
    @Embedded
    private Ingredients ingredients;

    public CustomerOrder() {
    }

    public CustomerOrder(Long id,
                         @NotNull String customerName,
                         @NotNull Ingredients ingredients) {
        this.id = id;
        this.customerName = customerName;
        this.ingredients = ingredients;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public CustomerOrder customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrder that = (CustomerOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, ingredients);
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
