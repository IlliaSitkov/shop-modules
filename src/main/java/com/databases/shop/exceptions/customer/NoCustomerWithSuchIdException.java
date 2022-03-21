package com.databases.shop.exceptions.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NoCustomerWithSuchIdException extends RuntimeException {

    public NoCustomerWithSuchIdException(Long id) {
        super("No customer with such id: "+id);
    }

}
