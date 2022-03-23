package com.databases.shop.mapstruct.dtos.filterBoundsDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import javax.validation.constraints.Min;

@Setter
public class OrderFilterBoundsDto {

    @JsonProperty("min_prod_num")
    @Min(0)
    private int minProdNum;

    @JsonProperty("max_prod_num")
    @Min(0)
    private int maxProdNum;

    @JsonProperty("min_cost")
    @Min(0)
    private double minCost;

    @JsonProperty("max_cost")
    @Min(0)
    private double maxCost;

    @JsonProperty("min_cat_num")
    @Min(0)
    private int minCatNum;

    @JsonProperty("max_cat_num")
    @Min(0)
    private int maxCatNum;






}
