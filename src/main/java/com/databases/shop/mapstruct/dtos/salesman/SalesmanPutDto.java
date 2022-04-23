package com.databases.shop.mapstruct.dtos.salesman;

import com.databases.shop.models.PersonName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import com.databases.shop.mapstruct.dtos.contacts.ContactsPutDto;

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

    @JsonProperty("date_of_birth")
    @NotNull
    private Date dateOfBirth;

    @JsonProperty("date_of_hiring")
    @NotNull
    private Date dateOfHiring;

}
