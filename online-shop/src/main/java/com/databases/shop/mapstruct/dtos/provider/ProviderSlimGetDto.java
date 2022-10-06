package com.databases.shop.mapstruct.dtos.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProviderSlimGetDto {

    @JsonProperty("edrpou")
    @NotNull
    private Long edrpou;

    @JsonProperty("name")
    @NotNull
    private String name;

}
