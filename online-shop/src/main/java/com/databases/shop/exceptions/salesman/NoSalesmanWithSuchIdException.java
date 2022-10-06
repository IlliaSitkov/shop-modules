package com.databases.shop.exceptions.salesman;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NoSalesmanWithSuchIdException extends RuntimeException {

    public NoSalesmanWithSuchIdException(Long id) {
        super("No salesman with such id: "+id);
    }

}
