package com.databases.shop.exceptions.productInOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class InsufficientProductQuantityException extends RuntimeException{

    public InsufficientProductQuantityException() {
        super("Insufficient product quantity!");
    }

}
