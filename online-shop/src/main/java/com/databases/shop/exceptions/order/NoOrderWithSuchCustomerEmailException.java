package com.databases.shop.exceptions.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NoOrderWithSuchCustomerEmailException extends RuntimeException {

    public NoOrderWithSuchCustomerEmailException(String email) {
        super("No order with customer who has such an email found: "+email);
    }
}
