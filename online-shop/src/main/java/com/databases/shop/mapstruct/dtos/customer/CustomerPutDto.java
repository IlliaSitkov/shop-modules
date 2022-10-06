package com.databases.shop.mapstruct.dtos.customer;

import com.databases.shop.mapstruct.dtos.contacts.ContactsPutDto;
import com.databases.shop.models.Address;
import com.databases.shop.models.PersonName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CustomerPutDto {

    @JsonProperty("full_name")
    @NotNull
    private PersonName personName;

    @JsonProperty("contacts")
    @NotNull
    private ContactsPutDto contacts;

    @JsonProperty("address")
    @NotNull
    private Address address;

}
