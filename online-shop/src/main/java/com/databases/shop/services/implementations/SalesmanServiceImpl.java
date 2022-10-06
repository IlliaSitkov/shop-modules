package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.salesman.NoSalesmanWithSuchIdException;
import com.databases.shop.exceptions.salesman.SalesmanRegistrationException;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.SalesmanFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanGetDto;
import com.databases.shop.mapstruct.dtos.salesman.SalesmanPostDto;
import com.databases.shop.mapstruct.mappers.SalesmanMapper;
import com.databases.shop.models.Order;
import com.databases.shop.models.Salesman;
import com.databases.shop.repositories.OrderRepository;
import com.databases.shop.repositories.SalesmanFilterRepository;
import com.databases.shop.repositories.SalesmanRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.services.interfaces.AdminService;
import com.databases.shop.services.interfaces.SalesmanService;
import com.databases.shop.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SalesmanServiceImpl implements SalesmanService {


    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private SalesmanFilterRepository salesmanFilterRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private SalesmanMapper salesmanMapper;

    @Autowired
    private AdminService adminService;

    @Override
    public Iterable<Salesman> findAll() {
        return salesmanRepository.getAll();
    }

    @Override
    public Salesman findById(Long id) {
        return salesmanRepository.findById(id).orElseThrow(() -> new NoSalesmanWithSuchIdException(id));
    }

    @Override
    public Salesman save(Salesman salesman) {
        utils.processSalesman(salesman);
        utils.checkPersonName(salesman.getPersonName());
        utils.checkContacts(salesman.getContacts());
        utils.checkDates(salesman.getDateOfBirth(), salesman.getDateOfHiring());
        return salesmanRepository.save(salesman);
    }

//    @Override
//    public Salesman save(Salesman salesman) {
//        utils.processSalesman(salesman);
//        utils.checkPersonName(salesman.getPersonName());
//        utils.checkContacts(salesman.getContacts());
//        if (usersWithEmailExist(salesman.getContacts().getEmail())) {
//            throw new SalesmanWithEmailAlreadyExistsException(salesman.getContacts().getEmail());
//        }
//        return salesmanRepository.save(salesman);
//    }

    public SalesmanGetDto saveSalesmanPostDto(SalesmanPostDto salesmanPostDto) {
        try {
            adminService.registerUser(salesmanPostDto.getContacts().getEmail(),salesmanPostDto.getPassword());
            adminService.saveUserToFirestore(salesmanPostDto.getContacts().getEmail(),salesmanPostDto.getRole());
            return salesmanMapper.salesmanToSalesmanGetDto(
                    save(salesmanMapper.salesmanSaveDtoToSalesman(
                                    salesmanMapper.salesmanPostDtoToSalesmanSaveDto(salesmanPostDto))));

        } catch (Exception e) {
            throw new SalesmanRegistrationException();
        }
    }

    @Override
    public SalesmanFilterBoundsDto getSalesmanFilterBounds() {
        MinMaxValues minMaxOrderCount = salesmanRepository.minMaxOrderCount();
        MinMaxValues minMaxSalesmanIncome = salesmanRepository.minMaxSalesmanIncome();

        SalesmanFilterBoundsDto salesmanFilterBoundsDto = new SalesmanFilterBoundsDto();

        salesmanFilterBoundsDto.setMinOrderCount((int)minMaxOrderCount.getMinValue());
        salesmanFilterBoundsDto.setMaxOrderCount((int)minMaxOrderCount.getMaxValue());

        salesmanFilterBoundsDto.setMinIncome(minMaxSalesmanIncome.getMinValue());
        salesmanFilterBoundsDto.setMaxIncome(minMaxSalesmanIncome.getMaxValue());

        return salesmanFilterBoundsDto;
    }

    @Override
    public Iterable<Salesman> getFilteredSalesmen(double income, int orders, boolean hasAllCategories) {
        return salesmanFilterRepository.filterSalesmen(income,orders,hasAllCategories);
    }

    @Override
    public Salesman findByEmail(String email) {
        return salesmanRepository.getByEmail(email);
    }

    @Override
    public Salesman update(Long id, Salesman salesman) {
        salesman.setPersonName(utils.processPersonName(salesman.getPersonName()));
        utils.checkPersonName(salesman.getPersonName());
        utils.checkPhoneNumber(salesman.getContacts().getPhoneNumber());
        utils.checkDates(salesman.getDateOfBirth(), salesman.getDateOfHiring());

        Salesman s = salesmanRepository.findById(id).orElseThrow(() -> new NoSalesmanWithSuchIdException(id));
        s.setPersonName(salesman.getPersonName());
        s.getContacts().setPhoneNumber(salesman.getContacts().getPhoneNumber());
        s.setDateOfBirth(salesman.getDateOfBirth());
        s.setDateOfHiring(salesman.getDateOfHiring());
        return salesmanRepository.save(s);
    }

    @Override
    public boolean existsByEmail(String email) {
        return salesmanRepository.existsByEmail(email);
    }

//    @Override
//    public boolean usersWithEmailExist(String email) {
//        return salesmanRepository.existsByEmail(email) ||
//                customerRepository.existsByEmail(email);
//    }

    @Override
    public boolean delete(Long id) {
        if (salesmanRepository.existsById(id)) {
            for (Order order: orderRepository.findBySalesmanId(id)){
                order.setSalesman(null);
                orderRepository.save(order);
            }
            Salesman s = salesmanRepository.getById(id);
            try {
                adminService.deleteUserAccountByEmail(s.getContacts().getEmail());
                adminService.deleteUserFromFirestore(s.getContacts().getEmail());
                salesmanRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Salesman's account could not have been deleted");
                return false;
            }
        };
        System.out.println("Salesman does not exist");
        return false;

    }


}
