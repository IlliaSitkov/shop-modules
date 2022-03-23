package com.databases.shop.services.interfaces;


import com.databases.shop.mapstruct.dtos.filterBoundsDtos.CustomerFilterBoundsDto;
import com.databases.shop.models.Customer;

public interface CustomerService {

    Iterable<Customer> findAll();

    Customer save(Customer customer);

    Customer update(Long id, Customer salesman);

    boolean delete(Long id);

//    CustomerGetDto saveCustomerPostDto(CustomerPostDto customerPostDto);

    CustomerFilterBoundsDto getCustomerFilterBounds();

    Iterable<Customer> getFilteredCustomers(int overallProdQuant, int avgOrderCost, long customerId, long productId, int boughtTimes);





}
