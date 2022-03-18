package com.databases.shop.services.interfaces;

import com.databases.shop.mapstruct.dtos.salesman.SalesmanGetDto;
import com.databases.shop.models.Salesman;

public interface SalesmanService {

    Iterable<Salesman> findAll();

    Salesman findById(Long id);

    Salesman save(Salesman salesman);

    Salesman update(Long id, Salesman salesman);

    void delete(Long id);
}
