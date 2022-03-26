package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.order.NoOrderWithSuchIdException;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.OrderFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.order.OrderPostDto;
import com.databases.shop.models.Customer;
import com.databases.shop.models.Order;
import com.databases.shop.models.OrderStatus;
import com.databases.shop.repositories.OrderFilterRepository;
import com.databases.shop.repositories.OrderRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.services.interfaces.CustomerService;
import com.databases.shop.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderFilterRepository orderFilterRepository;

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
        return orderRepository.getByCustomerEmail(email);
    }

    @Override
    public Order buyOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->  new NoOrderWithSuchIdException(orderId));
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setDate(getCurrentDate());
        return orderRepository.save(order);
    }

    @Override
    public Order save(OrderPostDto orderPostDto) {

        Order o = new Order();
        Customer customer = customerService.findById(orderPostDto.getCustomerId());

        o.setDate(getCurrentDate());
        o.setCustomer(customer);
        o.setStatus(OrderStatus.NEW);

        return orderRepository.save(o);
    }

    private Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();
    }
}
