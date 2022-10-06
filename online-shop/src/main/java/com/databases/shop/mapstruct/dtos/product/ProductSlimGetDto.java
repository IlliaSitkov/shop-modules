package com.databases.shop.mapstruct.dtos.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSlimGetDto {

    @JsonProperty("id")
    private Long articul;

    @JsonProperty("name")
    private String name;


}
