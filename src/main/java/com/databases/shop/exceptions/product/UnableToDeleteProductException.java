package com.databases.shop.exceptions.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnableToDeleteProductException extends RuntimeException {

    public UnableToDeleteProductException() {
        super("Product can not be deleted, because there are order lines of this product!");
    }
}
