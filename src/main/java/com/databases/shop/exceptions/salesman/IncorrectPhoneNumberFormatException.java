package com.databases.shop.exceptions.salesman;

public class IncorrectPhoneNumberFormatException extends RuntimeException {

    public IncorrectPhoneNumberFormatException(String phone) {
        super("Incorrect phone number format: "+phone);
    }
}
