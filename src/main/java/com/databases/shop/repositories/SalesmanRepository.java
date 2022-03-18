package com.databases.shop.repositories;

import com.databases.shop.models.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesmanRepository extends JpaRepository<Salesman,Long> {

    @Query("SELECT s FROM Salesman s")
    Iterable<Salesman> getAll();

    @Query("SELECT case when count(s)> 0 then true else false end FROM Salesman s WHERE s.contacts.email LIKE :email AND s.id = :id")
    boolean existsByEmailAndNotId(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT case when count(s)> 0 then true else false end FROM Salesman s WHERE s.contacts.email LIKE :email")
    boolean existsByEmail(@Param("email") String email);


}
