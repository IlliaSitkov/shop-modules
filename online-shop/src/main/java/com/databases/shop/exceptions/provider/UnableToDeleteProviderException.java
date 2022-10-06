package com.databases.shop.exceptions.provider;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnableToDeleteProviderException extends RuntimeException {

    public UnableToDeleteProviderException() {
        super("Provider can not be deleted, because there are products of this provider!");
    }
}
