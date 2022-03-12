package com.databases.shop.exceptions.strings;

public class InvalidAddressException extends RuntimeException {

    public InvalidAddressException(String explanation) {
        super(explanation);
    }
}
