package com.databases.shop.mapstruct.mappers;

import com.databases.shop.mapstruct.dtos.productInOrder.ProductInOrderGetDto;
import com.databases.shop.models.ProductInOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductInOrderMapper {

    ProductInOrderGetDto productInOrderToProductInOrderGetDto(ProductInOrder productInOrder);



}
