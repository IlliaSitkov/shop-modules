package com.databases.shop.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Contacts {

    @NotBlank
    @Max(25)
    @Column(name = "contacts_phone_number")
    private String phoneNumber;

    @NotBlank
    @Max(50)
    @Column(name = "contacts_email")
    private String email;

    public Contacts(String phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
