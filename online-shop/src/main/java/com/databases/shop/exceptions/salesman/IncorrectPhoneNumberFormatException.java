package com.databases.shop.exceptions.salesman;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class IncorrectPhoneNumberFormatException extends RuntimeException {

    public IncorrectPhoneNumberFormatException(String phone) {
        super("Incorrect phone number format: "+phone);
    }
}
