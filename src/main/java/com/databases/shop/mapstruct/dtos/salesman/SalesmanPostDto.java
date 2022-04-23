package com.databases.shop.mapstruct.dtos.salesman;

import com.databases.shop.models.Contacts;
import com.databases.shop.models.PersonName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@ToString
public class SalesmanPostDto {

    @JsonProperty("full_name")
    @NotNull
    private PersonName personName;

    @JsonProperty("contacts")
    @NotNull
    private Contacts contacts;

    @JsonProperty("date_of_birth")
    @NotNull
    private Date dateOfBirth;

    @JsonProperty("date_of_hiring")
    @NotNull
    private Date dateOfHiring;

    @JsonProperty("password")
    @NotNull
    private String password;

    @JsonProperty("role")
    @NotNull
    private String role;

}
