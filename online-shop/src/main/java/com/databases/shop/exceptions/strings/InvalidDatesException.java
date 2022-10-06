package com.databases.shop.exceptions.strings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDatesException extends RuntimeException {

    public InvalidDatesException(String explanation) {
        super(explanation);
    }
}
