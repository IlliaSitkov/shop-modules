package com.databases.shop.repositories.queryinterfaces;

public interface OrderGroupReportValues {

    String getStatus();
    int getOrderNum();
    double getOrderGroupCost();
    double getAvgOrderCost();

}
