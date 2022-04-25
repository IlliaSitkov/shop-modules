package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.category.UnableToDeleteCategoryException;
import com.databases.shop.exceptions.provider.NoProviderWithSuchEdrpou;
import com.databases.shop.exceptions.provider.ProviderIllegalArgumentException;
import com.databases.shop.exceptions.provider.UnableToDeleteProviderException;
import com.databases.shop.models.Address;
import com.databases.shop.models.Contacts;
import com.databases.shop.models.Provider;
import com.databases.shop.repositories.ProviderRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.services.interfaces.ProviderService;
import com.databases.shop.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    private Utils utils;

    @Override
    public Provider addProvider(String name, Address address, Contacts contacts) {
        name = utils.processString(name);
        utils.checkName(name);

        Iterable<Provider> providerWithSuchName = providerRepository.findByName(name);
        if (providerWithSuchName.iterator().hasNext())
            throw new ProviderIllegalArgumentException("Provider with such name already exists!");
        address = utils.processAddress(address);
        utils.checkAddress(address);
        contacts = utils.processContacts(contacts);
        utils.checkContactsProvider(contacts);

        return providerRepository.save(new Provider(name, address, contacts));
    }

    @Override
    public Provider addProvider(Provider provider) {
        provider.setEdrpou(-1L);
        return addProvider(provider.getName(), provider.getAddress(), provider.getContacts());
    }

    @Override
    public boolean providerExistsByEdrpou(Long edrpou) {
        return providerRepository.existsById(edrpou);
    }

    @Override
    public boolean deleteProvider(Long edrpou) {
        if(!providerExistsByEdrpou(edrpou)) throw new NoProviderWithSuchEdrpou(edrpou);
        try {
            providerRepository.deletePByEdrpou(edrpou);
        }
        catch (Exception e) {
            throw new UnableToDeleteProviderException();
        }
        return true;
    }

    @Override
    public Provider updateProvider(Long edrpou, String name, Address address, Contacts contacts) {
        name = utils.processString(name);
        utils.checkName(name);

        Iterable<Provider> providersWithSuchName = providerRepository.findByNameAndNotEdrpou(edrpou, name);
        if (providersWithSuchName.iterator().hasNext())
            throw new ProviderIllegalArgumentException("Provider with such name already exists!");
        String finalName = name;
        Address finalAddress = utils.processAddress(address);
        utils.checkAddress(finalAddress);
        Contacts finalContacts = utils.processContacts(contacts);
        utils.checkContactsProvider(finalContacts);
        return providerRepository.findById(edrpou).map((provider) -> {
            if (provider.getContacts() == null)
                provider.setContacts(new Contacts(null, null));
            if (nothingChanged(provider, finalName, finalAddress, finalContacts))
                return provider;

            provider.setName(finalName);
            provider.setAddress(finalAddress);
            provider.setContacts(finalContacts);

            // return providerRepository.save(provider);
            providerRepository.update(edrpou, finalName, finalAddress.getCountry(), finalAddress.getRegion(), finalAddress.getCity(), finalAddress.getStreet(), finalAddress.getApartment(), finalContacts.getPhoneNumber(), finalContacts.getEmail());
            return provider;
        }).orElseGet(() -> {
            return providerRepository.save(new Provider(edrpou, finalName, finalAddress, finalContacts));
        });
    }

    private boolean nothingChanged(Provider provider, String name, Address address, Contacts contacts) {
        System.out.println(provider.getContacts());
        return provider.getName().equals(name) && provider.getAddress().equals(address) &&
                provider.getContacts().equals(contacts);
    }

    @Override
    public Provider updateProvider(Provider provider) {
        return updateProvider(provider.getEdrpou(), provider.getName(), provider.getAddress(), provider.getContacts());
    }

    @Override
    public Provider updateProviderNoCheck(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Provider getProviderByEdrpou(Long edrpou) {
        return providerRepository.getPByEdrpou(edrpou).orElseThrow(() -> new NoProviderWithSuchEdrpou(edrpou));
    }

    @Override
    public Iterable<Provider> getAll() {
        return providerRepository.getAll();
    }

    @Override
    public Iterable<Provider> getProvidersFilteredByProductsQuantity(int quantity) {
        return providerRepository.findHavingQuantityOfProductsSoldBigger(quantity);
    }

    @Override
    public Iterable<Provider> getProvidersFilteredByProductsQuantityAndJustSalesmenOfProvider(int quantity, Long providerEdrpou) {
        return providerRepository.findHavingJustSalesmenOfProviderAndQuantityOfProductsSoldBigger(quantity, providerEdrpou);
    }

    @Override
    public MinMaxValues getMinMaxProductsQuantity() {
        return providerRepository.minMaxProductsQuantity();
    }

    @Override
    public Iterable<Provider> findName(String name) {
        return providerRepository.findByName(name);
    }

}
