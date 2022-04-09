package com.databases.shop.repositories.queryinterfaces;

import java.util.Date;

public interface OrderReportValues {


    Long getOrderId();
    String getOrderStatus();
    Date getDateDone();
    int getProductNamesNum();
    int getCategoriesNum();
    int getProvidersNum();
    double getCost();
    double getAvgProductPrice();
    int getOverallProductsNum();



}
