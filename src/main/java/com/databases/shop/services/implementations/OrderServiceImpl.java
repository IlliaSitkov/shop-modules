package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.order.NoOrderWithSuchCustomerEmailException;
import com.databases.shop.exceptions.order.NoOrderWithSuchIdException;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.OrderFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.order.OrderPostDto;
import com.databases.shop.models.Customer;
import com.databases.shop.models.Order;
import com.databases.shop.models.OrderStatus;
import com.databases.shop.models.Salesman;
import com.databases.shop.repositories.OrderFilterRepository;
import com.databases.shop.repositories.OrderRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.repositories.queryinterfaces.OrderGroupReportValues;
import com.databases.shop.repositories.queryinterfaces.OrderReportValues;
import com.databases.shop.repositories.queryinterfaces.ProductReportValues;
import com.databases.shop.services.interfaces.CustomerService;
import com.databases.shop.services.interfaces.OrderService;
import com.databases.shop.services.interfaces.SalesmanService;
import com.databases.shop.utils.Utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SalesmanService salesmanService;

    @Autowired
    private OrderFilterRepository orderFilterRepository;

    @Autowired
    private Utils utils;

    @Override
    public OrderFilterBoundsDto getOrderFilterBounds(Long customerId, Long salesmanId) {
        MinMaxValues minMaxProdNum = orderRepository.getMinMaxProdNameNumber(customerId,salesmanId);
        MinMaxValues minMaxCost = orderRepository.getMinMaxCost(customerId,salesmanId);
        MinMaxValues minMaxCatNum = orderRepository.getMinMaxCategoryNumber(customerId,salesmanId);

        OrderFilterBoundsDto dto = new OrderFilterBoundsDto();

        dto.setMinProdNum((int)minMaxProdNum.getMinValue());
        dto.setMaxProdNum((int)minMaxProdNum.getMaxValue());

        dto.setMinCost(minMaxCost.getMinValue());
        dto.setMaxCost(minMaxCost.getMaxValue());

        dto.setMinCatNum((int)minMaxCatNum.getMinValue());
        dto.setMaxCatNum((int)minMaxCatNum.getMaxValue());

        return dto;
    }

    @Override
    public Iterable<Order> getFilteredOrders(List<String> statuses,
                                             int prodNameNum,
                                             int categNum,
                                             double orderCost,
                                             String date,
                                             boolean dateFilterEnabled,
                                             long salesmanId,
                                             long customerId,
                                             long providerIdBad,
                                             long orderId,
                                             long providerId,
                                             int prodNumK) {
        return orderFilterRepository.filterOrders(statuses,prodNameNum,categNum,orderCost,date,dateFilterEnabled,salesmanId,customerId,providerIdBad,orderId,providerId,prodNumK);
    }

    @Override
    public Iterable<Order> findAll() {
        return orderRepository.getAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->  new NoOrderWithSuchIdException(id));
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order findByCustomerEmail(String email) {
        System.out.println(email);
        Order o = orderRepository.getByCustomerEmail(email);
        if (o == null) throw new NoOrderWithSuchCustomerEmailException(email);
        return o;
    }

    @Override
    public Order buyOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->  new NoOrderWithSuchIdException(orderId));
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setDate(utils.getCurrentDate());
        return orderRepository.save(order);
    }

    @Override
    public Order save(OrderPostDto orderPostDto) {

        Order o = new Order();
        Customer customer = customerService.findById(orderPostDto.getCustomerId());

        o.setDate(utils.getCurrentDate());
        o.setCustomer(customer);
        o.setStatus(OrderStatus.NEW);

        return orderRepository.save(o);
    }

    @Override
    public Order markOrderAsDone(Long orderId, Long salesmanId) {

        Order o = orderRepository.findById(orderId).orElseThrow(() -> new NoOrderWithSuchIdException(orderId));

        Salesman s = salesmanService.findById(salesmanId);

        o.setStatus(OrderStatus.DONE);
        o.setDate(utils.getCurrentDate());
        o.setSalesman(s);

        return orderRepository.save(o);
    }










}
