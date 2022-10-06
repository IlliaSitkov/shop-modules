package com.databases.shop.models;

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
//@EqualsAndHashCode
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

    private boolean stringsAreEqual(String str1, String str2) {
        if (str1 == null)
            return str2 == null;
        if (str2 == null)
            return false;
        return str1.equals(str2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contacts)) return false;
        Contacts contacts = (Contacts) o;
        return stringsAreEqual(getPhoneNumber(), contacts.getPhoneNumber()) &&
                stringsAreEqual(getEmail(), contacts.getEmail());
    }
}
