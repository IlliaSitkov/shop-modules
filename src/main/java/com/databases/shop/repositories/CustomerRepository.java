package com.databases.shop.repositories;

import com.databases.shop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c " +
            "FROM Customer c")
    Iterable<Customer> getAll();

    @Query("SELECT case when count(c)> 0 then true else false end FROM Customer c WHERE c.contacts.email LIKE :email")
    boolean existsByEmail(@Param("email") String email);

}
