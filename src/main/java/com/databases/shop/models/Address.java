package com.databases.shop.models;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
@Getter
public class Address {

    @NotBlank
    @Max(25)
    @Column(name = "addr_country")
    private String country;

    @NotBlank
    @Max(25)
    @Column(name = "addr_region")
    private String region;

    @NotBlank
    @Max(25)
    @Column(name = "addr_city")
    private String city;

    @NotBlank
    @Max(60)
    @Column(name = "addr_street")
    private String street;

    @NotBlank
    @Max(8)
    @Column(name = "addr_apartment")
    private String apartment;

}
