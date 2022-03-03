package com.databases.shop.services.implementations;

import com.databases.shop.models.Address;
import com.databases.shop.models.Contacts;
import com.databases.shop.models.Provider;
import com.databases.shop.services.interfaces.ProviderService;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {

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
    public Iterable<Provider> getProviderByPartName(String name) throws Exception {
        return null;
    }

    @Override
    public Iterable<Provider> getAll() {
        return null;
    }
}
