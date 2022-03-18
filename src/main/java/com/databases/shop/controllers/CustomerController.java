package com.databases.shop.controllers;

import com.databases.shop.models.Customer;
import com.databases.shop.services.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/customers")
public class CustomerController {


    @Autowired
    private CustomerService customerService;


    @GetMapping
    public Iterable<Customer> getAllCustomers() {
        return customerService.findAll();
    }


}
