package com.databases.shop.mapstruct.dtos.productInOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductInOrderPostDto {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private int quantity;

}
