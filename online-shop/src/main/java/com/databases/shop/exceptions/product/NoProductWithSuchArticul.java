package com.databases.shop.exceptions.product;

public class NoProductWithSuchArticul extends RuntimeException{

    public NoProductWithSuchArticul(Long articul) {
        super("Subject with articul '"+ articul +"' has not been found!");
    }
}
