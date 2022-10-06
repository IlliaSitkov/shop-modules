package com.databases.shop.mapstruct.dtos.salesman;

import com.databases.shop.mapstruct.dtos.contacts.ContactsPutDto;
import com.databases.shop.models.PersonName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class SalesmanPutDto {

    @JsonProperty("full_name")
    @NotNull
    private PersonName personName;

    @JsonProperty("contacts")
    @NotNull
    private ContactsPutDto contacts;

    @JsonProperty("dateOfBirth")
    @NotNull
    private Date dateOfBirth;

    @JsonProperty("dateOfHiring")
    @NotNull
    private Date dateOfHiring;

}
