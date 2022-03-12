package com.databases.shop.exceptions.strings;

public class InvalidContactsException extends RuntimeException {

    public InvalidContactsException(String explanation) {
        super(explanation);
    }
}
