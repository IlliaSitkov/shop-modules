package com.databases.shop.services.implementations;

import com.databases.generator.ReportGenerator;
import com.databases.model.OrderGroupReportValues;
import com.databases.model.OrderReportValues;
import com.databases.model.ProductReportValues;
import com.databases.shop.repositories.OrderRepository;
import com.databases.shop.repositories.ProductRepository;
import com.databases.shop.services.interfaces.ReportService;
import com.databases.shop.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportGenerator reportGenerator;

    @Autowired
    private Utils utils;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void generateOrdersReport(String dateStart, String dateEnd) {
        LocalDate dStart = dateStart == null || dateStart.isEmpty() ? utils.convertToLocalDateViaInstant(orderRepository.getMinDate()) : LocalDate.parse(dateStart);
        LocalDate dEnd = dateEnd == null || dateEnd.isEmpty() ? utils.convertToLocalDateViaInstant(orderRepository.getMaxDate()): LocalDate.parse(dateEnd);

        Date dSt = utils.convertToDateViaSqlTimestamp(dStart);
        Date dEn = utils.convertToDateViaSqlTimestamp(dEnd);

        double fullIncome = orderRepository.getFullIncome(dStart,dEnd);
        int ordersQuantity = orderRepository.getOrdersNum(dStart,dEnd);
        double averageOrderCost = orderRepository.getAvgOrderCost(dStart,dEnd);
        Iterable<OrderGroupReportValues> orderGroupReportValues = orderRepository.getOrderGroupReportValues(dStart,dEnd);
        Iterable<OrderReportValues> orderReportValues = orderRepository.getOrderReportValues(dStart,dEnd);


        reportGenerator.generateOrdersReport(
                dSt, dEn, "report-generator/src/main/resources/static/ordersReport.pdf",
                fullIncome, ordersQuantity, averageOrderCost,
                orderGroupReportValues, orderReportValues
        );

    }

    @Override
    public void generateProductsReport(String dateStart, String dateEnd) {
        LocalDate dStart = dateStart == null || dateStart.isEmpty() ? utils.convertToLocalDateViaInstant(orderRepository.getMinDate()) : LocalDate.parse(dateStart);
        LocalDate dEnd = dateEnd == null || dateEnd.isEmpty() ? utils.convertToLocalDateViaInstant(orderRepository.getMaxDate()): LocalDate.parse(dateEnd);

        Date dSt = utils.convertToDateViaSqlTimestamp(dStart);
        Date dEn = utils.convertToDateViaSqlTimestamp(dEnd);

        int soldProductsQuantity = productRepository.getSoldProductsQuant(dStart,dEnd);
        Iterable<ProductReportValues> productReportValues = productRepository.productReport(dStart,dEnd);

        reportGenerator.generateProductsReport(
                dSt, dEn, "report-generator/src/main/resources/static/productsReport.pdf",
                soldProductsQuantity, productReportValues
        );
    }
}