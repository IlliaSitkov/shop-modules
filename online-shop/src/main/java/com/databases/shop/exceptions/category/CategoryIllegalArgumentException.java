package com.databases.shop.exceptions.category;

public class CategoryIllegalArgumentException extends RuntimeException{

    public CategoryIllegalArgumentException(String explanation) {
        super(explanation);
    }
}
