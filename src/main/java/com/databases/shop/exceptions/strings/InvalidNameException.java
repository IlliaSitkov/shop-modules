package com.databases.shop.exceptions.strings;

public class InvalidNameException extends RuntimeException {

    public InvalidNameException(String explanation) {
        super(explanation);
    }
}
