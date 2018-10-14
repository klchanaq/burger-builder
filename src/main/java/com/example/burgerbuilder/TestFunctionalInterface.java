package com.example.burgerbuilder;

/* Notes:
*  #1 The only usage of @FunctionalInterface is to indicate there will be only one abstract method declared on the interface,
*  otherwise system will throw a compilation error.
* */

@FunctionalInterface
public interface TestFunctionalInterface<T, U, R> {

    public abstract R apply(T t, U u) throws Exception;

    // Note #1 : Uncommenting the following method will throw a compilation error
    // public abstract R applyTest (T t, U u) throws Exception;

}