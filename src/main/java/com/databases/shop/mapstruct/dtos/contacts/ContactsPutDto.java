package com.databases.shop.mapstruct.dtos.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactsPutDto {

    @JsonProperty("phoneNumber")
    private String phoneNumber;
}
