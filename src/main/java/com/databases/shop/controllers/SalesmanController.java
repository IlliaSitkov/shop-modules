package com.databases.shop.controllers;

import com.databases.shop.exceptions.salesman.SalesmanRegistrationException;
import com.databases.shop.mapstruct.dtos.dataDtos.SalesmanFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanGetDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanPostDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanPutDto;
import com.databases.shop.mapstruct.mappers.SalesmanMapper;
import com.databases.shop.services.interfaces.AdminService;
import com.databases.shop.services.interfaces.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@Validated
@RequestMapping("/salesmen")
public class SalesmanController {

    @Autowired
    private SalesmanService salesmanService;

    @Autowired
    private SalesmanMapper salesmanMapper;

    @Autowired
    public SalesmanController(SalesmanService salesmanService, SalesmanMapper salesmanMapper) {
        this.salesmanService = salesmanService;
        this.salesmanMapper = salesmanMapper;
    }

    @GetMapping
    public Iterable<SalesmanGetDto> getAllSalesmen() {
        return salesmanMapper.salesmenToSalesmenGetDto(salesmanService.findAll());
    }

    @PostMapping
    public SalesmanGetDto saveSalesman(@Valid @RequestBody SalesmanPostDto salesmanPostDto) {
        return salesmanService.saveSalesmanPostDto(salesmanPostDto);
    }

    @PutMapping("/{id}")
    public SalesmanGetDto updateSalesman(@PathVariable("id") Long id,@Valid @RequestBody SalesmanPutDto salesmanPutDto) {
        return salesmanMapper.salesmanToSalesmanGetDto(
                salesmanService.update(id,salesmanMapper.salesmanPutDtoToSalesman(salesmanPutDto)));
    }

    @DeleteMapping("/{id}")
    public boolean deleteSalesman(@PathVariable("id") Long id) {
        return salesmanService.delete(id);
    }

    @GetMapping("/email")
    public boolean usersWithEmailExist(@RequestParam(name = "email") String email) {
        System.out.println(email);
        return salesmanService.usersWithEmailExist(email);
    }

    @GetMapping("/filterBounds")
    public SalesmanFilterBoundsDto getFilterBounds() {
        return salesmanService.getSalesmanFilterBounds();
    }

}
