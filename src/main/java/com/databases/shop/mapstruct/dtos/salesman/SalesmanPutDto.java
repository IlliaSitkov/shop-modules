package com.databases.shop.mapstruct.dtos.salesman;

import com.databases.shop.models.PersonName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import com.databases.shop.mapstruct.dtos.contacts.ContactsPutDto;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SalesmanPutDto {

    @JsonProperty("full_name")
    @NotNull
    private PersonName personName;

    @JsonProperty("contacts")
    @NotNull
    private ContactsPutDto contacts;

}
