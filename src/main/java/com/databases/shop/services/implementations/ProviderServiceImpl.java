package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.category.CategoryIllegalArgumentException;
import com.databases.shop.exceptions.provider.ProviderIllegalArgumentException;
import com.databases.shop.models.Address;
import com.databases.shop.models.Category;
import com.databases.shop.models.Contacts;
import com.databases.shop.models.Provider;
import com.databases.shop.repositories.ProviderRepository;
import com.databases.shop.services.interfaces.ProviderService;
import com.databases.shop.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    private Utils utils;

    @Override
    public Provider addProvider(String name, Address address, Contacts contacts) {
        return null;
    }

    @Override
    public Provider addProvider(Provider provider) {
        return null;
    }

    @Override
    public boolean providerExistsById(Long edrpou) {
        return false;
    }

    @Override
    public boolean deleteProvider(Long edrpou) {
        return false;
    }

    @Override
    public Provider updateProvider(Long edrpou, String name, Address address, Contacts contacts) {
        return null;
    }

    @Override
    public Provider updateProvider(Provider provider) {
        return null;
    }

    @Override
    public Provider updateProviderNoCheck(Provider provider) {
        return null;
    }

    @Override
    public Provider getProviderById(Long edrpou) {
        return null;
    }

    @Override
    public Iterable<Provider> getAll() {
        return null;
    }
}
