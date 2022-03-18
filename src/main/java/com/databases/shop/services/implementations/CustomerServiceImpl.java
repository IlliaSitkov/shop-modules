package com.databases.shop.services.implementations;

import com.databases.shop.models.Customer;
import com.databases.shop.repositories.CustomerRepository;
import com.databases.shop.services.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.getAll();
    }
}
