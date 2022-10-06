package com.databases.generator;


import com.databases.model.OrderGroupReportValues;
import com.databases.model.OrderReportValues;
import com.databases.model.ProductReportValues;

import java.util.Date;

public interface ReportGenerator {

    void generateOrdersReport(Date dateStart,
                                     Date dateEnd,
                                     String filePath,
                                     double fullIncome,
                                     int ordersQuantity,
                                     double averageOrderCost,
                                     Iterable<OrderGroupReportValues> orderGroupReportValues,
                                     Iterable<OrderReportValues> orderReportValues);
    void generateProductsReport(Date dateStart,
                                Date dateEnd,
                                String filePath,
                                int soldProductsQuantity,
                                Iterable<ProductReportValues> productReportValues);

}
