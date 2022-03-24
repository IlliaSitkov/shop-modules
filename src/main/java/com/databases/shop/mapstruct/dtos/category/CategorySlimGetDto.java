package com.databases.shop.mapstruct.dtos.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategorySlimGetDto {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("name")
    @NotNull
    private String name;
}
