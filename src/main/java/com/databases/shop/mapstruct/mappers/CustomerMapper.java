package com.databases.shop.mapstruct.mappers;

import com.databases.shop.mapstruct.dtos.customer.CustomerGetDto;
import com.databases.shop.mapstruct.dtos.customer.CustomerPostDto;
import com.databases.shop.mapstruct.dtos.customer.CustomerPutDto;
import com.databases.shop.mapstruct.dtos.customer.CustomerSlimGetDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanGetDto;
import com.databases.shop.models.Customer;
import com.databases.shop.models.Salesman;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerGetDto customerToCustomerGetDto(Customer customer);

    Iterable<CustomerGetDto> customersToCustomersGetDto(Iterable<Customer> customers);

    CustomerSlimGetDto customerToCustomerSlimGetDto(Customer customer);

    Iterable<CustomerSlimGetDto> customersToCustomersSlimGetDto(Iterable<Customer> customers);

    Customer customerPostDtoToCustomer(CustomerPostDto customerPostDto);

    Customer customerPutDtoToCustomer(CustomerPutDto customerPutDto);

}
