package com.databases.shop.exceptions.strings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAddressException extends RuntimeException {

    public InvalidAddressException(String explanation) {
        super(explanation);
    }
}
