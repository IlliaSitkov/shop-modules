package com.databases.shop.repositories;

import com.databases.shop.models.Category;
import com.databases.shop.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    @Query("SELECT p " +
            "FROM Provider p " +
            "WHERE LOWER(p.name) LIKE LOWER( :name)")
    Iterable<Provider> findByName(@Param("name") String name);

    @Query("SELECT p " +
            "FROM Provider p " +
            "WHERE LOWER(p.name) LIKE LOWER( :name) AND NOT p.edrpou = :edrpou")
    Iterable<Provider> findByNameAndNotEdrpou(@Param("edrpou") long edrpou, @Param("name") String name);
}