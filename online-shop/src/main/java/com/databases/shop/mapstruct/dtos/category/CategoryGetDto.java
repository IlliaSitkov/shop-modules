package com.databases.shop.mapstruct.dtos.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryGetDto {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("description")
    @NotNull
    private String description;
}
