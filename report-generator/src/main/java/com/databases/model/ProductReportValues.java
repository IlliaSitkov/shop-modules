package com.databases.model;

public interface ProductReportValues {

    Long getArticul();
    String getName();
    int getSoldQuantity();
    double getSoldCost();
    int getAverageQuantityInOrder();
    int getQuantityOfCustomers();
}
