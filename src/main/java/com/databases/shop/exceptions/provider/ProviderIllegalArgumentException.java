package com.databases.shop.exceptions.provider;

public class ProviderIllegalArgumentException extends RuntimeException{

    public ProviderIllegalArgumentException(String explanation) {
        super(explanation);
    }
}
