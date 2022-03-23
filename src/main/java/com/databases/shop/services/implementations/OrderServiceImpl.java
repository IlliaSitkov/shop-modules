package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.order.NoOrderWithSuchIdException;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.OrderFilterBoundsDto;
import com.databases.shop.models.Order;
import com.databases.shop.repositories.OrderFilterRepository;
import com.databases.shop.repositories.OrderRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderFilterRepository orderFilterRepository;

    @Override
    public OrderFilterBoundsDto getOrderFilterBounds() {
        MinMaxValues minMaxProdNum = orderRepository.getMinMaxProdNameNumber();
        MinMaxValues minMaxCost = orderRepository.getMinMaxCost();
        MinMaxValues minMaxCatNum = orderRepository.getMinMaxCategoryNumber();

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
}
