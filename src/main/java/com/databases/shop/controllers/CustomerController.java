package com.databases.shop.controllers;

import com.databases.shop.mapstruct.dtos.customer.CustomerGetDto;
import com.databases.shop.mapstruct.dtos.customer.CustomerPostDto;
import com.databases.shop.mapstruct.dtos.customer.CustomerPutDto;
import com.databases.shop.mapstruct.dtos.dataDtos.CustomerFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.dataDtos.SalesmanFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanGetDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanPostDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanPutDto;
import com.databases.shop.mapstruct.mappers.CustomerMapper;
import com.databases.shop.models.Customer;
import com.databases.shop.services.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/customers")
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping
    public Iterable<CustomerGetDto> getAllCustomers() {
        return customerMapper.customersToCustomersGetDto(
                customerService.findAll());
    }

    @GetMapping("/filter")
    public Iterable<CustomerGetDto> getFilteredCustomers(
            @RequestParam("overallQuant") int overallQuant,
            @RequestParam("avgCost") int avgCost,
            @RequestParam("productId") long productId,
            @RequestParam("boughtTimes") int boughtTimes,
            @RequestParam("customerId") long customerId) {
        return customerMapper.customersToCustomersGetDto(
                customerService.getFilteredCustomers(overallQuant,avgCost,customerId,productId,boughtTimes));
    }

    @DeleteMapping("/{id}")
    public boolean deleteCustomer(@PathVariable("id") Long id) {
        return customerService.delete(id);
    }

    @PutMapping("/{id}")
    public CustomerGetDto updateCustomer(@PathVariable("id") Long id, @Valid @RequestBody CustomerPutDto customerPutDto) {
        return customerMapper.customerToCustomerGetDto(
                customerService.update(id,customerMapper.customerPutDtoToCustomer(customerPutDto)));
    }

    @PostMapping
    public CustomerGetDto saveCustomer(@Valid @RequestBody CustomerPostDto customerPostDto) {
        return customerMapper.customerToCustomerGetDto(
                customerService.save(customerMapper.customerPostDtoToCustomer(customerPostDto)));
    }

    @GetMapping("/filterBounds")
    public CustomerFilterBoundsDto getFilterBounds() {
        return customerService.getCustomerFilterBounds();
    }



}
