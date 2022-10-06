package com.databases.shop.mapstruct.dtos.order;

import com.databases.shop.models.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class OrderSlimGetDto {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("date")
    @NotNull
    private Date date;

    @JsonProperty("status")
    @NotNull
    private OrderStatus status;

}
