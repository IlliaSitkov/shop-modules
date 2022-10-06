package com.databases.shop.mapstruct.dtos.order;

import com.databases.shop.mapstruct.dtos.customer.CustomerGetDto;
import com.databases.shop.mapstruct.dtos.productInOrder.ProductInOrderGetDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@ToString
public class OrderBasketGetDto {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("customer")
    @NotNull
    private CustomerGetDto customer;

    @JsonProperty("order_lines")
    @NotNull
    private Set<ProductInOrderGetDto> products;

    @JsonProperty("order_cost")
    @NotNull
    private double cost;

    public void setProducts(Set<ProductInOrderGetDto> products) {
        this.products = products;
        this.products.forEach(p -> this.cost += p.getCost());
    }
}
