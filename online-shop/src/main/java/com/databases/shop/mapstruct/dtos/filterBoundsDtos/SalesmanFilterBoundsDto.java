package com.databases.shop.mapstruct.dtos.filterBoundsDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import javax.validation.constraints.Min;

@Setter
public class SalesmanFilterBoundsDto {

    @JsonProperty("min_order")
    @Min(0)
    private int minOrderCount;

    @JsonProperty("max_order")
    @Min(0)
    private int maxOrderCount;

    @JsonProperty("min_income")
    @Min(0)
    private double minIncome;

    @JsonProperty("max_income")
    @Min(0)
    private double maxIncome;

}
