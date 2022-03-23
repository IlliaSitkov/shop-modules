package com.databases.shop.mapstruct.mappers;

import com.databases.shop.mapstruct.dtos.order.OrderSlimGetDto;
import com.databases.shop.models.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderSlimGetDto orderToOrderSlimGetDto(Order order);

    Iterable<OrderSlimGetDto> ordersToOrdersSlimGetDto(Iterable<Order> orders);


}
