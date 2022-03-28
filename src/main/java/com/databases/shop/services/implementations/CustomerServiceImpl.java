package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.customer.NoCustomerWithSuchIdException;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.CustomerFilterBoundsDto;
import com.databases.shop.models.Customer;
import com.databases.shop.models.Order;
import com.databases.shop.models.OrderStatus;
import com.databases.shop.repositories.CustomerFilterRepository;
import com.databases.shop.repositories.CustomerRepository;
import com.databases.shop.repositories.OrderRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.services.interfaces.AdminService;
import com.databases.shop.services.interfaces.CustomerService;
import com.databases.shop.services.interfaces.OrderService;
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

    @Autowired
    private OrderRepository orderRepository;

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
            for (Order order: orderRepository.findByCustomerId(id)){
                if (order.getStatus() == OrderStatus.NEW) {
                    orderRepository.delete(order);
                }
                else {
                    order.setCustomer(null);
                    orderRepository.save(order);
                }
            }
            customerRepository.deleteById(id);
            return true;
            /*Customer c = customerRepository.getById(id);
            try {
                adminService.deleteUserAccountByEmail(c.getContacts().getEmail());
                adminService.deleteUserFromFirestore(c.getContacts().getEmail());
                customerRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                return false;
            }*/
        };
        return false;
    }

    @Override
    public CustomerFilterBoundsDto getCustomerFilterBounds() {

        MinMaxValues minMaxAvgOrderCost = customerRepository.getMinMaxAvgOrderCost();
        MinMaxValues minMaxOverallQuantity = customerRepository.getMinMaxOverallQuantity();

        CustomerFilterBoundsDto dto = new CustomerFilterBoundsDto();

        dto.setMaxAvg(minMaxAvgOrderCost.getMaxValue());
        dto.setMinAvg(minMaxAvgOrderCost.getMinValue());

        dto.setMaxOverall((int)minMaxOverallQuantity.getMaxValue());
        dto.setMinOverall((int)minMaxOverallQuantity.getMinValue());

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

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.getByEmail(email);
    }

    @Override
    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new NoCustomerWithSuchIdException(customerId));
    }
}
