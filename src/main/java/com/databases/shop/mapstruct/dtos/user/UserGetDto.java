package com.databases.shop.mapstruct.dtos.user;

import com.databases.shop.models.Address;
import com.databases.shop.models.Contacts;
import com.databases.shop.models.PersonName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@Setter
@Getter
public class UserGetDto {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("full_name")
    @NotNull
    private PersonName personName;

    @JsonProperty("contacts")
    @NotNull
    private Contacts contacts;

    @JsonProperty("address")
    @Nullable
    private Address address;


}
