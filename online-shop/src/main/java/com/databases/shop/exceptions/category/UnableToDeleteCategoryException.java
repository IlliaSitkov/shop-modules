package com.databases.shop.exceptions.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnableToDeleteCategoryException extends RuntimeException {

    public UnableToDeleteCategoryException() {
        super("Category can not be deleted, because there are products of this category!");
    }
}
