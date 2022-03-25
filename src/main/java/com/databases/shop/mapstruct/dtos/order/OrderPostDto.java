package com.databases.shop.mapstruct.dtos.order;

import com.databases.shop.mapstruct.dtos.customer.CustomerGetDto;
import com.databases.shop.mapstruct.dtos.productInOrder.ProductInOrderGetDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanGetDto;
import com.databases.shop.models.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
public class OrderPostDto {


    @JsonProperty("customerId")
    @NotNull
    private Long customerId;


}
