package com.databases.shop.exceptions.salesman;

public class SalesmanWithEmailAlreadyExistsException extends RuntimeException {

    public SalesmanWithEmailAlreadyExistsException(String email) {
        super("Salesman with such email '"+ email +"' already exists!");
    }


}
