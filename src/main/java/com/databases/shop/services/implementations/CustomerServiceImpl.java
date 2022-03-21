package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.customer.NoCustomerWithSuchIdException;
import com.databases.shop.mapstruct.dtos.dataDtos.CustomerFilterBoundsDto;
import com.databases.shop.models.Customer;
import com.databases.shop.repositories.CustomerFilterRepository;
import com.databases.shop.repositories.CustomerRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxAvgOrderCost;
import com.databases.shop.repositories.queryinterfaces.MinMaxOverallQuantity;
import com.databases.shop.services.interfaces.AdminService;
import com.databases.shop.services.interfaces.CustomerService;
import com.databases.shop.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerFilterRepository customerFilterRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private AdminService adminService;


    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.getAll();
    }

    @Override
    public Customer save(Customer customer) {

        utils.processCustomer(customer);
        utils.checkPersonName(customer.getPersonName());
        utils.checkContacts(customer.getContacts());
        utils.checkAddress(customer.getAddress());

        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        customer.setPersonName(utils.processPersonName(customer.getPersonName()));
        utils.checkPersonName(customer.getPersonName());
        utils.checkPhoneNumber(customer.getContacts().getPhoneNumber());

        Customer c = customerRepository.findById(id).orElseThrow(() -> new NoCustomerWithSuchIdException(id));
        c.setPersonName(customer.getPersonName());
        c.getContacts().setPhoneNumber(customer.getContacts().getPhoneNumber());
        return customerRepository.save(c);
    }

    @Override
    public boolean delete(Long id) {
        if (customerRepository.existsById(id)) {
            Customer c = customerRepository.getById(id);
            try {
                adminService.deleteUserAccountByEmail(c.getContacts().getEmail());
                adminService.deleteUserFromFirestore(c.getContacts().getEmail());
                customerRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                return false;
            }
        };
        return false;
    }

    @Override
    public CustomerFilterBoundsDto getCustomerFilterBounds() {

        MinMaxAvgOrderCost minMaxAvgOrderCost = customerRepository.getMinMaxAvgOrderCost();
        MinMaxOverallQuantity minMaxOverallQuantity = customerRepository.getMinMaxOverallQuantity();

        System.out.println(minMaxAvgOrderCost.getMinAvgOrderCost());
        System.out.println(minMaxAvgOrderCost.getMaxAvgOrderCost());


        CustomerFilterBoundsDto dto = new CustomerFilterBoundsDto();

        dto.setMaxAvg(minMaxAvgOrderCost.getMaxAvgOrderCost());
        dto.setMinAvg(minMaxAvgOrderCost.getMinAvgOrderCost());

        dto.setMaxOverall(minMaxOverallQuantity.getMaxOverallQuantity());
        dto.setMinOverall(minMaxOverallQuantity.getMinOverallQuantity());

        return dto;
    }

//    @Override
//    public CustomerGetDto saveCustomerPostDto(CustomerPostDto customerPostDto) {
//        try {
////            adminService.registerUser(customerPostDto.getContacts().getEmail(),customerPostDto.getPassword());
////            adminService.saveUserToFirestore(customerPostDto.getContacts().getEmail(),customerPostDto.getRole());
//            return customer.salesmanToSalesmanGetDto(
//                    save(salesmanMapper.salesmanSaveDtoToSalesman(
//                            salesmanMapper.salesmanPostDtoToSalesmanSaveDto(salesmanPostDto))));
//
//        } catch (Exception e) {
//            throw new SalesmanRegistrationException();
//        }
//    }

    @Override
    public Iterable<Customer> getFilteredCustomers(int overallProdQuant, int avgOrderCost, long customerId, long productId, int boughtTimes) {
        return customerFilterRepository.filterCustomers(overallProdQuant,avgOrderCost,customerId,productId,boughtTimes);
    }
}
