package com.databases.shop.exceptions.salesman;

public class NoSalesmanWithSuchIdException extends RuntimeException {


    public NoSalesmanWithSuchIdException(Long id) {
        super("No salesman with such id: "+id);
    }

}
