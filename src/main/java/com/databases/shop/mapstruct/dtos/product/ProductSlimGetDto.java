package com.databases.shop.mapstruct.dtos.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductSlimGetDto {

    @JsonProperty("id")
    private Long articul;

    @JsonProperty("name")
    private String name;


}
