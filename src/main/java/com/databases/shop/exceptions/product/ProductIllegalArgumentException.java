package com.databases.shop.exceptions.product;

public class ProductIllegalArgumentException extends RuntimeException{

    public ProductIllegalArgumentException(String explanation) {
        super(explanation);
    }
}
