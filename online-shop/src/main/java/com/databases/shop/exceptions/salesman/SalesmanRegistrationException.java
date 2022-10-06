package com.databases.shop.exceptions.salesman;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class SalesmanRegistrationException extends RuntimeException {

    public SalesmanRegistrationException() {
        super("Salesman registration failed!");
    }

}