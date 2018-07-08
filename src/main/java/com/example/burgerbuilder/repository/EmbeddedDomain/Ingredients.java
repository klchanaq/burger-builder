package com.example.burgerbuilder.repository.EmbeddedDomain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Ingredients implements Serializable {

    private static final long serialVersionUID = 221234L;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(nullable = false)
    private Integer bacon;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(nullable = false)
    private Integer cheese;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(nullable = false)
    private Integer salad;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(nullable = false)
    private Integer meat;

    public Ingredients() {
        this.bacon = new Integer(0);
        this.cheese = new Integer(0);
        this.salad = new Integer(0);
        this.meat = new Integer(0);
    }

    public Ingredients(@NotNull @Min(value = 0) @Max(value = 10) Integer bacon,
                       @NotNull @Min(value = 0) @Max(value = 10) Integer cheese,
                       @NotNull @Min(value = 0) @Max(value = 10) Integer salad,
                       @NotNull @Min(value = 0) @Max(value = 10) Integer meat) {
        this.bacon = bacon;
        this.cheese = cheese;
        this.salad = salad;
        this.meat = meat;
    }

    public Integer getBacon() {
        return bacon;
    }

    public Ingredients bacon(Integer bacon) {
        this.bacon = bacon;
        return this;
    }

    public void setBacon(Integer bacon) {
        this.bacon = bacon;
    }

    public Integer getCheese() {
        return cheese;
    }

    public Ingredients cheese(Integer cheese) {
        this.cheese = cheese;
        return this;
    }

    public void setCheese(Integer cheese) {
        this.cheese = cheese;
    }

    public Integer getSalad() {
        return salad;
    }

    public Ingredients salad(Integer salad) {
        this.salad = salad;
        return this;
    }

    public void setSalad(Integer salad) {
        this.salad = salad;
    }

    public Integer getMeat() {
        return meat;
    }

    public Ingredients meat(Integer meat) {
        this.meat = meat;
        return this;
    }

    public void setMeat(Integer meat) {
        this.meat = meat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return Objects.equals(bacon, that.bacon) &&
                Objects.equals(cheese, that.cheese) &&
                Objects.equals(salad, that.salad) &&
                Objects.equals(meat, that.meat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bacon, cheese, salad, meat);
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "bacon=" + bacon +
                ", cheese=" + cheese +
                ", salad=" + salad +
                ", meat=" + meat +
                '}';
    }
}
