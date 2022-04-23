package com.databases.shop.services.interfaces;

public interface ReportService {

    void generateOrdersReport(String dateStart, String dateEnd);
    void generateProductsReport(String dateStart, String dateEnd);

}
