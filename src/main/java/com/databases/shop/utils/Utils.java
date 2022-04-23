package com.databases.shop.utils;

import com.databases.shop.exceptions.salesman.IncorrectPhoneNumberFormatException;
import com.databases.shop.exceptions.strings.InvalidAddressException;
import com.databases.shop.exceptions.strings.InvalidContactsException;
import com.databases.shop.exceptions.strings.InvalidDatesException;
import com.databases.shop.exceptions.strings.InvalidNameException;
import com.databases.shop.exceptions.product.ProductIllegalArgumentException;
import com.databases.shop.models.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Utils {

    public String processString(String str) {
        return str == null ? null : str.replaceAll("\\s+", " ").trim();
    }

    public boolean isInvalidName(String name) {
        return name == null || name.isEmpty();
    }

    public void checkName(String name) {
        if (isInvalidName(name)) {
            throw new InvalidNameException(Values.INVALID_NAME);
        }
    }

    public PersonName processPersonName(PersonName personName) {
        return new PersonName(processString(personName.getName()),processString(personName.getLastname()),processString(personName.getSurname()));
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

    public boolean isInvalidAddress(Address address) {
        return address.getCountry() == null || address.getRegion() == null
                || address.getCity() == null || address.getStreet() == null
                || address.getApartment() == null ||
                address.getCountry().isEmpty() || address.getRegion().isEmpty()
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
        return contacts.getPhoneNumber() == null || contacts.getEmail() == null ||
                contacts.getPhoneNumber().isEmpty() || contacts.getEmail().isEmpty()
                || !isValidPhoneNumber(contacts.getPhoneNumber())
                || !contacts.getEmail().matches("[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}");
    }

    public boolean isValidPhoneNumber(String phone) {
        return phone.matches("[(]?\\d+[)]?[-\\s]?\\d+[-\\s]?\\d+([-\\s]\\d+)?");
        //return true;
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

    public void checkContactsProvider(Contacts contacts) {
        if (contacts.getPhoneNumber() != null && (contacts.getPhoneNumber().isEmpty() || !isValidPhoneNumber(contacts.getPhoneNumber()))) {
            throw new InvalidContactsException(Values.INVALID_CONTACTS);
        }
        if (contacts.getEmail() != null && (contacts.getEmail().isEmpty() || !contacts.getEmail().matches("[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}"))) {
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

    public List<Long> stringToListLong(String str) {
        return Arrays.stream(str.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }



    public Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTime();
    }

    private boolean isInvalidDateOfBirth(Date dateOfBirth, LocalDate currentDateL) {
        LocalDate dateOfBirthL = convertToLocalDateViaInstant(dateOfBirth);
        return (dateOfBirthL.isAfter(currentDateL.minusYears(17)) || (dateOfBirthL.isBefore(currentDateL.minusYears(120))));
    }

    private boolean isInvalidDateOfHiring(Date dateOfHiring, LocalDate currentDateL) {
        LocalDate dateOfHiringL = convertToLocalDateViaInstant(dateOfHiring);
        return dateOfHiringL.isBefore(currentDateL.minusYears(100));
    }

    public void checkDates(Date dateOfBirth, Date dateOfHiring) {
        LocalDate currentDate = convertToLocalDateViaInstant(getCurrentDate());
        if (isInvalidDateOfBirth(dateOfBirth, currentDate) || isInvalidDateOfHiring(dateOfHiring, currentDate)) {
            throw new InvalidDatesException(Values.INVALID_DATE);
        }
    }

    public Date convertToDateViaSqlTimestamp(LocalDate dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert.atStartOfDay());
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
