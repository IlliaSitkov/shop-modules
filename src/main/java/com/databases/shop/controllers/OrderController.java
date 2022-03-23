package com.databases.shop.controllers;

import com.databases.shop.mapstruct.dtos.filterBoundsDtos.OrderFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.order.OrderSlimGetDto;
import com.databases.shop.mapstruct.mappers.OrderMapper;
import com.databases.shop.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:3000"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;


    @GetMapping("/filter")
    public Iterable<OrderSlimGetDto> getFilteredOrders(@RequestParam List<String> statuses,
                                                       @RequestParam int prodNameNum,
                                                       @RequestParam int catNum,
                                                       @RequestParam double orderCost,
                                                       @RequestParam(required = false) String date,
                                                       @RequestParam boolean dateFilterEnabled,
                                                       @RequestParam long salesmanId,
                                                       @RequestParam long customerId,
                                                       @RequestParam long badProviderId,
                                                       @RequestParam long orderId,
                                                       @RequestParam long providerId,
                                                       @RequestParam int prodNumK) {
        return orderMapper.ordersToOrdersSlimGetDto(orderService.getFilteredOrders(
                statuses, prodNameNum,catNum,orderCost,date,dateFilterEnabled,salesmanId,customerId,badProviderId,orderId,providerId,prodNumK));
    }

    @GetMapping("/filterBounds")
    public OrderFilterBoundsDto getFilterBounds() {
        return orderService.getOrderFilterBounds();
    }


    @GetMapping("/slim")
    public Iterable<OrderSlimGetDto> getAllSlimOrders() {
        return orderMapper.ordersToOrdersSlimGetDto(orderService.findAll());
    }




}
