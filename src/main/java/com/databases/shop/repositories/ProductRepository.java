package com.databases.shop.repositories;

import com.databases.shop.models.Product;
import com.databases.shop.repositories.queryinterfaces.MinMaxPrice;
import com.databases.shop.repositories.queryinterfaces.MinMaxProductsQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Query(value =
            "SELECT *\n" +
            "FROM product\n" +
            "WHERE :quantity <= quantity\n" +
            "AND :price <= price\n" +
            "AND provider_fk IN :providersEdrpous\n" +
            "AND category_fk IN :categoriesIds", nativeQuery = true)
    Iterable<Product> findFilteredProducts(int quantity, double price, List<Long> providersEdrpous, List<Long> categoriesIds);

    @Query(value =
            "SELECT COALESCE (MIN(quantity),0) AS minQuantity, COALESCE (MAX(quantity),0) AS maxQuantity\n" +
                    "FROM\n" +
                    "   (SELECT articul, quantity\n" +
                    "    FROM product\n" +
                    "    GROUP BY articul) AS ProductsQuantities", nativeQuery = true)
    MinMaxProductsQuantity minMaxProductsQuantity();

    @Query(value =
            "SELECT COALESCE (MIN(price),0) AS minPrice, COALESCE (MAX(price),0) AS maxPrice\n" +
                    "FROM\n" +
                    "   (SELECT articul, price\n" +
                    "    FROM product\n" +
                    "    GROUP BY articul) AS ProductsPrices", nativeQuery = true)
    MinMaxPrice minMaxProductsPrice();
}
