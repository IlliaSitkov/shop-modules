package com.databases.shop.exceptions;

public class InvalidNameException extends RuntimeException {

    public InvalidNameException(String explanation) {
        super(explanation);
    }
}
