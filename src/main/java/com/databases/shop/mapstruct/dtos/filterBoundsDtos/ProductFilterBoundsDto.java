package com.databases.shop.mapstruct.dtos.filterBoundsDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import javax.validation.constraints.Min;

@Setter
public class ProductFilterBoundsDto {

    @JsonProperty("minQuantity")
    @Min(0)
    private int minQuantity;

    @JsonProperty("maxQuantity")
    @Min(0)
    private int maxQuantity;

    @JsonProperty("minPrice")
    @Min(0)
    private double minPrice;

    @JsonProperty("maxPrice")
    @Min(0)
    private double maxPrice;
}
