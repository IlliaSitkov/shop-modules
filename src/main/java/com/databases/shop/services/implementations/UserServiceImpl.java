package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.user.NoUserWithSuchEmailException;
import com.databases.shop.mapstruct.dtos.user.UserGetDto;
import com.databases.shop.mapstruct.mappers.UserMapper;
import com.databases.shop.services.interfaces.CustomerService;
import com.databases.shop.services.interfaces.SalesmanService;
import com.databases.shop.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private SalesmanService salesmanService;

    private CustomerService customerService;

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(SalesmanService salesmanService, CustomerService customerService,UserMapper userMapper) {
        this.salesmanService = salesmanService;
        this.customerService = customerService;
        this.userMapper = userMapper;
    }

    @Override
    public UserGetDto findUserByEmail(String email) {

        if (salesmanService.existsByEmail(email)) {
            return userMapper.salesmanToUserGetDto(salesmanService.findByEmail(email));
        } else if (customerService.existsByEmail(email)) {
            return userMapper.customerToUserGetDto(customerService.findByEmail(email));
        }
        throw new NoUserWithSuchEmailException(email);
    }
}
