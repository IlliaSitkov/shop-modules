package com.databases.shop.mapstruct.mappers;

import com.databases.shop.mapstruct.dtos.salesman.SalesmanGetDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanPostDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanPutDto;
import com.databases.shop.models.Contacts;
import com.databases.shop.models.Salesman;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesmanMapper {

    SalesmanGetDto salesmanToSalesmanGetDto(Salesman salesman);

    Iterable<SalesmanGetDto> salesmenToSalesmenGetDto(Iterable<Salesman> salesmen);

    Salesman salesmanPostDtoToSalesman(SalesmanPostDto salesmanPostDto);

    Salesman salesmanPutDtoToSalesman(SalesmanPutDto salesmanPutDto);









}
