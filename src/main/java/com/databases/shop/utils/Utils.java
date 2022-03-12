package com.databases.shop.utils;

import com.databases.shop.exceptions.strings.InvalidAddressException;
import com.databases.shop.exceptions.strings.InvalidContactsException;
import com.databases.shop.exceptions.strings.InvalidNameException;
import com.databases.shop.exceptions.product.ProductIllegalArgumentException;
import com.databases.shop.models.Address;
import com.databases.shop.models.Contacts;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public String processString(String str) {
        return str.replaceAll("\\s+", " ").trim();
    }

    public boolean isInvalidName(String name) {
        return name.isEmpty();
    }

    public void checkName(String name) {
        if (isInvalidName(name)) {
            throw new InvalidNameException(Values.INVALID_NAME);
        }
    }

    public Address processAddress(Address address) {
        return new Address(processString(address.getCountry()), processString(address.getRegion()),
                processString(address.getCity()), processString(address.getStreet()),
                processString(address.getApartment()));
    }

    //TODO: is region necessary?
    public boolean isInvalidAddress(Address address) {
        return address.getCountry().isEmpty() || address.getRegion().isEmpty()
                || address.getCity().isEmpty() || address.getStreet().isEmpty()
                || address.getApartment().isEmpty();
    }

    public void checkAddress(Address address) {
        if (isInvalidAddress(address)) {
            throw new InvalidAddressException(Values.INVALID_ADDRESS);
        }
    }

    public Contacts processContacts(Contacts contacts) {
        return new Contacts(processString(contacts.getPhoneNumber()), processString(contacts.getEmail()));
    }

    public boolean isInvalidContacts(Contacts contacts) {
        return contacts.getPhoneNumber().isEmpty() || contacts.getEmail().isEmpty()
                || !contacts.getPhoneNumber().matches("[(]?\\d+[)]?[-\\s]?\\d+[-\\s]?\\d+([-\\s]\\d+)?")
                || !contacts.getEmail().matches("[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}");
    }

    public void checkContacts(Contacts contacts) {
        if (isInvalidContacts(contacts)) {
            throw new InvalidContactsException(Values.INVALID_CONTACTS);
        }
    }

    public void checkQuantOfProduct(int quantOfProduct) {
        if (quantOfProduct < 0) {
            throw new ProductIllegalArgumentException("Quantity of product should not be negative!");
        }
    }

    public void checkPriceOfProduct(double priceOfProduct) {
        if (priceOfProduct < 0) {
            throw new ProductIllegalArgumentException("Price of product should not be negative!");
        }
    }
}
