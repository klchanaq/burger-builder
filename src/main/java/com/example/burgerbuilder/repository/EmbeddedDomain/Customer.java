package com.example.burgerbuilder.repository.EmbeddedDomain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

//@Entity
//@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 27821924L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @NotNull
    @Size(min = 0, max = 30)
    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = true)
    private String email;

    @NotNull
    @Embedded
    private Address address;

}
