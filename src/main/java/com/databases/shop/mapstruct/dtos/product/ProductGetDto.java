package com.databases.shop.mapstruct.dtos.product;

import com.databases.shop.mapstruct.dtos.category.CategoryGetDto;
import com.databases.shop.mapstruct.dtos.provider.ProviderGetDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductGetDto {

    @JsonProperty("articul")
    @NotNull
    private Long articul;

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("description")
    @NotNull
    private String description;

    @JsonProperty("quantity")
    @NotNull
    private int quantity;

    @JsonProperty("price")
    @NotNull
    private double price;

    @JsonProperty("provider")
    private ProviderGetDto provider;

    @JsonProperty("category")
    private CategoryGetDto category;
}
