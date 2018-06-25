package com.example.burgerbuilder.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "author")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer age;

    // @JsonFormat( pattern = "dd/mm/yy" ) this will only be modified on Jackson JSON <-> Object
    @NotNull
    private LocalDate registerDate;

    @NotNull
    private ZonedDateTime lastSignDateTime;

//    @OneToMany(mappedBy = "author")
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<Blog> blogs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Author name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public Author age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public Author registerDate(LocalDate registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public ZonedDateTime getLastSignDateTime() {
        return lastSignDateTime;
    }

    public Author lastSignDateTime(ZonedDateTime lastSignDateTime) {
        this.lastSignDateTime = lastSignDateTime;
        return this;
    }

    public void setLastSignDateTime(ZonedDateTime lastSignDateTime) {
        this.lastSignDateTime = lastSignDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) &&
                Objects.equals(name, author.name) &&
                Objects.equals(age, author.age) &&
                Objects.equals(registerDate, author.registerDate) &&
                Objects.equals(lastSignDateTime, author.lastSignDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, registerDate, lastSignDateTime);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", registerDate=" + registerDate +
                ", lastSignDateTime=" + lastSignDateTime +
                '}';
    }
}
