package com.databases.shop.exceptions.salesman;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SalesmanWithEmailAlreadyExistsException extends RuntimeException {

    public SalesmanWithEmailAlreadyExistsException(String email) {
        super("Salesman with such email '"+ email +"' already exists!");
    }


}
