package com.databases.shop.mapstruct.dtos.productInOrder;

import com.databases.shop.mapstruct.dtos.order.OrderSlimGetDto;
import com.databases.shop.mapstruct.dtos.product.ProductGetDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ProductInOrderGetDto {

    @JsonProperty("product")
    @NotNull
    private ProductGetDto product;

    @JsonProperty("order")
    @NotNull
    private OrderSlimGetDto order;

    @JsonProperty("price")
    @NotNull
    private double price;

    @JsonProperty("quantity")
    @NotNull
    private double quantity;

    @JsonProperty("line_cost")
    @NotNull
    private double cost;

    public void setPrice(double price) {
        this.price = price;
        this.cost = this.price*this.quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        this.cost = this.price*this.quantity;
    }
}
