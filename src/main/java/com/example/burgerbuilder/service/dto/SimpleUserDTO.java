package com.example.burgerbuilder.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO representing a user, with his/her authorities.
 */
public class SimpleUserDTO implements Serializable {

    private static final long serialVersionUID = 2124L;

    @NotBlank
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotBlank
    @Size(min = 4, max = 100)
    private String password;

    public String getEmail() {
        return email;
    }

    public SimpleUserDTO email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public SimpleUserDTO password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleUserDTO that = (SimpleUserDTO) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "SimpleUserDTO{" +
                "email='" + email + '\'' +
                '}';
    }

}
