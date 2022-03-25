package com.databases.shop.mapstruct.mappers;

import com.databases.shop.mapstruct.dtos.user.UserGetDto;
import com.databases.shop.models.Customer;
import com.databases.shop.models.Salesman;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserGetDto salesmanToUserGetDto(Salesman salesman);
    UserGetDto customerToUserGetDto(Customer salesman);


}
