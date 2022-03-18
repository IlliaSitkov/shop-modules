package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.salesman.NoSalesmanWithSuchIdException;
import com.databases.shop.exceptions.salesman.SalesmanWithEmailAlreadyExistsException;
import com.databases.shop.models.Salesman;
import com.databases.shop.repositories.CustomerRepository;
import com.databases.shop.repositories.SalesmanRepository;
import com.databases.shop.services.interfaces.SalesmanService;
import com.databases.shop.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SalesmanServiceImpl implements SalesmanService {


    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Utils utils;

    @Override
    public Iterable<Salesman> findAll() {
        return salesmanRepository.getAll();
    }

    @Override
    public Salesman findById(Long id) {
        return null;
    }

    @Override
    public Salesman save(Salesman salesman) {
        utils.processSalesman(salesman);
        utils.checkPersonName(salesman.getPersonName());
        utils.checkContacts(salesman.getContacts());
        if (usersWithEmailExist(salesman.getContacts().getEmail())) {
            throw new SalesmanWithEmailAlreadyExistsException(salesman.getContacts().getEmail());
        }
        return salesmanRepository.save(salesman);
    }

    @Override
    public Salesman update(Long id, Salesman salesman) {
        salesman.setPersonName(utils.processPersonName(salesman.getPersonName()));
        utils.checkPersonName(salesman.getPersonName());
        utils.checkPhoneNumber(salesman.getContacts().getPhoneNumber());

        Salesman s = salesmanRepository.findById(id).orElseThrow(() -> new NoSalesmanWithSuchIdException(id));
        s.setPersonName(salesman.getPersonName());
        s.getContacts().setPhoneNumber(salesman.getContacts().getPhoneNumber());
        return salesmanRepository.save(s);
    }

    @Override
    public boolean usersWithEmailExist(String email) {
        return salesmanRepository.existsByEmail(email) ||
                customerRepository.existsByEmail(email);
    }

    @Override
    public void delete(Long id) {
        salesmanRepository.deleteById(id);
    }


}
