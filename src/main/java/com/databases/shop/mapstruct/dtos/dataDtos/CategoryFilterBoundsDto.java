package com.databases.shop.mapstruct.dtos.dataDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import javax.validation.constraints.Min;

@Setter
public class CategoryFilterBoundsDto {

    @JsonProperty("minCustomersQuant")
    @Min(0)
    private int minCustomersQuant;

    @JsonProperty("maxCustomersQuant")
    @Min(0)
    private int maxCustomersQuant;

    @JsonProperty("minProductsQuant")
    @Min(0)
    private int minProductsQuant;

    @JsonProperty("maxProductsQuant")
    @Min(0)
    private int maxProductsQuant;
}
