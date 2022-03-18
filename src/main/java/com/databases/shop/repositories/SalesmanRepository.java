package com.databases.shop.repositories;

import com.databases.shop.models.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesmanRepository extends JpaRepository<Salesman,Long> {

    @Query("SELECT s FROM Salesman s ORDER BY s.personName.surname")
    Iterable<Salesman> getAll();

    @Query("SELECT case when count(s)> 0 then true else false end FROM Salesman s WHERE s.contacts.email LIKE :email AND s.id = :id")
    boolean existsByEmailAndNotId(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT case when count(s)> 0 then true else false end FROM Salesman s WHERE s.contacts.email LIKE :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'DONE' AND o.salesman.id = :id")
    int salesmanOrderQuantity(@Param("id") Long id);

    @Query("SELECT s FROM Salesman s WHERE :quantity <= (SELECT COUNT(o) FROM Order o WHERE o.status = 'DONE' AND o.salesman.id = s.id)")
    int haveGEDoneOrderQuantity(@Param("quantity") int quantity);

}
