package com.databases.shop.mapstruct.dtos.customer;

import com.databases.shop.models.Address;
import com.databases.shop.models.Contacts;
import com.databases.shop.models.PersonName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CustomerSlimGetDto {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("full_name")
    @NotNull
    private PersonName personName;
}
