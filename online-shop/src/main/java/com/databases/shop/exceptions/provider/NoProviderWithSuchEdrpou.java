package com.databases.shop.exceptions.provider;

public class NoProviderWithSuchEdrpou extends RuntimeException{

    public NoProviderWithSuchEdrpou(Long edrpou) {
        super("Provider with EDRPOU '"+ edrpou +"' has not been found!");
    }
}
