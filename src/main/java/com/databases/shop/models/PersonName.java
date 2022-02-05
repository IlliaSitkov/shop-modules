package com.databases.shop.models;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
public class PersonName {

    @NotBlank
    @Max(15)
    @Column(name = "person_name")
    private String name;

    @Column(name = "person_lastname")
    @Max(15)
    private String lastName;

    @NotBlank
    @Column(name = "person_surname")
    @Max(15)
    private String surname;

}
