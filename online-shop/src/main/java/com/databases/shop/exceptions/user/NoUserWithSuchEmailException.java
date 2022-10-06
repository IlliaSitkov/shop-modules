package com.databases.shop.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NoUserWithSuchEmailException extends RuntimeException {

    public NoUserWithSuchEmailException(String email) {
        super("No user with email "+email+" found!");
    }
}
