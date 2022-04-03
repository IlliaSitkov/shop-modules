package com.databases.shop.services.interfaces;

import com.databases.shop.models.Address;
import com.databases.shop.models.Contacts;
import com.databases.shop.models.Provider;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;

public interface ProviderService {

    Provider addProvider(String name, Address address, Contacts contacts);
    Provider addProvider(Provider provider);

    boolean providerExistsByEdrpou(Long edrpou);
    //boolean providerExistsByName(String name);

    boolean deleteProvider(Long edrpou); // throws ProviderNotFoundException;
    Provider updateProvider(Long edrpou, String name, Address address, Contacts contacts);
    Provider updateProvider(Provider provider);
    Provider updateProviderNoCheck(Provider provider);
    Provider getProviderByEdrpou(Long edrpou); // throws ProviderNotFoundException;

    //Provider getProviderByName(String name) throws Exception;
    Iterable<Provider> getAll();

    Iterable<Provider> getProvidersFilteredByProductsQuantity(int quantity);
    Iterable<Provider> getProvidersFilteredByProductsQuantityAndJustSalesmenOfProvider(int quantity, Long providerEdrpou);
    MinMaxValues getMinMaxProductsQuantity();

    Iterable<Provider> findName(String name);
}
