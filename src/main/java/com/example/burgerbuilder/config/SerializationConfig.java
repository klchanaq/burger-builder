package com.example.burgerbuilder.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.validation.ConstraintViolationProblemModule;

@Configuration
public class SerializationConfig {

    /*
    * Support for Hibernate types in Jackson, for turn off lazy-loading on Jackson
    * Notes: Jackson will map POJOs eagerly by default ( iterate through the List Proxy even it is marked with LAZY ).
    *        Hibernate5Module will look into the Annotations to see whether the annotation is considered LAZY LOADING.
    *        Skip its serialization process if Jackson finds the LAZY LOADING information.
    *        Jackson will not provide Session Creation, Transaction Opening for LAZY LOADING Serialization/Deserialization.
    *        The jobs Session Creation, Transaction Opening for LAZY LOADING Serialization/Deserialization is done by Spring's OpenSessionInView.
    */

    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    /*
     * Jackson Afterburner module to speed up serialization/deserialization.
     */
    @Bean
    public AfterburnerModule afterburnerModule() {
        return new AfterburnerModule();
    }

    /*
     * Module for serialization/deserialization of RFC7807 Problem.
     */
    @Bean
    ProblemModule problemModule() {
        return new ProblemModule();
    }

    /*
     * Module for serialization/deserialization of ConstraintViolationProblem.
     */
    @Bean
    ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }

}
