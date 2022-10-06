package com.databases.shop.mapstruct.dtos.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class OrderPostDto {


    @JsonProperty("customerId")
    @NotNull
    private Long customerId;


}
