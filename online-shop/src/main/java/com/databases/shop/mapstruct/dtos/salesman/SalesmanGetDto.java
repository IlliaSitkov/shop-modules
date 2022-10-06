package com.databases.shop.mapstruct.dtos.salesman;

import com.databases.shop.models.Contacts;
import com.databases.shop.models.PersonName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class SalesmanGetDto {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("full_name")
    @NotNull
    private PersonName personName;

    @JsonProperty("contacts")
    @NotNull
    private Contacts contacts;

    @JsonProperty("dateOfBirth")
    @NotNull
    private Date dateOfBirth;

    @JsonProperty("dateOfHiring")
    @NotNull
    private Date dateOfHiring;

}
