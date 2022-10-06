package com.databases.shop.exceptions.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NoOrderWithSuchIdException extends RuntimeException {

    public NoOrderWithSuchIdException(Long id) {
        super("No order with such id: "+id);
    }

}
