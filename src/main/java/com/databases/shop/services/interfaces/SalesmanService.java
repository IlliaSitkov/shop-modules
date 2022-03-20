package com.databases.shop.services.interfaces;

import com.databases.shop.mapstruct.dtos.dataDtos.SalesmanFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanGetDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanPostDto;
import com.databases.shop.models.Salesman;

public interface SalesmanService {

    Iterable<Salesman> findAll();

    Salesman findById(Long id);

    Salesman save(Salesman salesman);

    Salesman update(Long id, Salesman salesman);

    boolean usersWithEmailExist(String email);

//    Iterable<Salesman> haveGEDoneOrderQuantity(int quantity);

    boolean delete(Long id);

    SalesmanGetDto saveSalesmanPostDto(SalesmanPostDto salesmanPostDto);

    SalesmanFilterBoundsDto getSalesmanFilterBounds();

    Iterable<Salesman> getFilteredSalesmen(double income,int orders,boolean hasAllCategories);


}
