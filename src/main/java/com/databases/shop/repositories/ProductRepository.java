package com.databases.shop.repositories;

import com.databases.shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p " +
           "FROM Product p " +
           "WHERE LOWER(p.name) LIKE LOWER( :name)")
    Iterable<Product> findByName(@Param("name") String name);

    @Query("SELECT p " +
           "FROM Product p " +
           "WHERE LOWER(p.name) LIKE LOWER( :name) AND NOT p.articul = :articul")
    Iterable<Product> findByNameAndNotArticul(@Param("articul") long articul, @Param("name") String name);


}
