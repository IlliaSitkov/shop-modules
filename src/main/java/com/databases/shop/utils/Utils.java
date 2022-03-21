package com.databases.shop.utils;

import com.databases.shop.exceptions.salesman.IncorrectPhoneNumberFormatException;
import com.databases.shop.exceptions.strings.InvalidAddressException;
import com.databases.shop.exceptions.strings.InvalidContactsException;
import com.databases.shop.exceptions.strings.InvalidNameException;
import com.databases.shop.exceptions.product.ProductIllegalArgumentException;
import com.databases.shop.models.*;
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

    public PersonName processPersonName(PersonName personName) {
        String lastname = personName.getLastname() == null ? null : processString(personName.getLastname());
        return new PersonName(processString(personName.getName()),lastname,processString(personName.getSurname()));
    }

    public void checkPersonName(PersonName personName) {
        if (isInvalidName(personName.getName()) ||
//                isInvalidName(personName.getLastname()) ||
                isInvalidName(personName.getSurname())) {
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
                || !isValidPhoneNumber(contacts.getPhoneNumber())
                || !contacts.getEmail().matches("[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}");
    }

    public boolean isValidPhoneNumber(String phone) {
//        return phone.matches("[(]?\\d+[)]?[-\\s]?\\d+[-\\s]?\\d+([-\\s]\\d+)?");
        return true;
    }

    public void checkPhoneNumber(String phone) {
        if (!isValidPhoneNumber(phone)) {
            throw new IncorrectPhoneNumberFormatException(phone);
        }
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

    public void processSalesman(Salesman salesman) {
        salesman.setContacts(processContacts(salesman.getContacts()));
        salesman.setPersonName(processPersonName(salesman.getPersonName()));
    }

    public void processCustomer(Customer customer) {
        customer.setContacts(processContacts(customer.getContacts()));
        customer.setPersonName(processPersonName(customer.getPersonName()));
        customer.setAddress(processAddress(customer.getAddress()));
    }
}
