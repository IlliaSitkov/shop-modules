package com.databases.shop.repositories;

import com.databases.shop.models.Provider;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    @Query(value =
            "SELECT *\n" +
            "FROM provider\n" +
            "ORDER BY name", nativeQuery = true)
    Iterable<Provider> getAll();

    @Query(value =
            "SELECT *\n" +
            "FROM provider\n" +
            "WHERE edrpou = :edrpou", nativeQuery = true)
    Optional<Provider> getPByEdrpou(long edrpou);

    @Query(value =
            "SELECT *\n" +
            "FROM provider\n" +
            "WHERE LOWER(name) LIKE LOWER(:name)", nativeQuery = true)
    Iterable<Provider> findByName(String name);

    @Query(value =
            "SELECT *\n" +
            "FROM provider\n" +
            "WHERE LOWER(name) LIKE LOWER(:name) AND NOT edrpou = :edrpou", nativeQuery = true)
    Iterable<Provider> findByNameAndNotEdrpou(long edrpou, String name);

    @Transactional
    @Modifying
    @Query(value =
            "DELETE FROM provider\n" +
            "WHERE edrpou = :edrpou", nativeQuery = true)
    void deletePByEdrpou(long edrpou);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE provider\n" +
            "SET name = :name,\n" +
            "addr_country = :country,\n" +
            "addr_region = :region,\n" +
            "addr_city = :city,\n" +
            "addr_street = :street,\n" +
            "addr_apartment = :apartment,\n" +
            "contacts_phone_number = :phoneNumber,\n" +
            "contacts_email = :email\n" +
            "WHERE edrpou = :edrpou", nativeQuery = true)
    void update(Long edrpou, String name, String country, String region, String city, String street, String apartment, String phoneNumber, String email);

    @Query(value =
            "SELECT *\n" +
            "FROM provider\n" +
            "WHERE :quantity <= (SELECT COALESCE(SUM(prod_quantity),0)\n" +
                                "FROM product_in_order\n" +
                                "WHERE order_id IN(\n" +
                                    "SELECT order_id\n" +
                                    "FROM order_t\n" +
                                    "WHERE status = 'DONE'\n" +
                                "AND product_articul IN(\n" +
                                    "SELECT articul\n" +
                                    "FROM product\n" +
                                    "WHERE provider_fk = edrpou)))\n" +
            "ORDER BY name", nativeQuery = true)
    Iterable<Provider> findHavingQuantityOfProductsSoldBigger(@Param("quantity") int quantity);

    @Query(value =
            "SELECT COALESCE(MIN(COALESCE(products_quantity,0)),0) AS minValue, COALESCE(MAX(COALESCE(products_quantity,0)),0) AS maxValue\n" +
                    "FROM provider LEFT OUTER JOIN (\n" +
                    "    SELECT provider_fk, SUM(prod_quantity) AS products_quantity\n" +
                    "    FROM product INNER JOIN product_in_order pio ON product.articul = pio.product_articul\n" +
                    "              INNER JOIN order_t ON order_t.id = pio.order_id\n" +
                    "    WHERE status = 'DONE'\n" +
                    "    GROUP BY provider_fk) AS ProductsQuantities ON provider.edrpou = provider_fk", nativeQuery = true)
    MinMaxValues minMaxProductsQuantity();

    @Query(value =
            "SELECT *\n" +
            "FROM provider prov\n" +
            "WHERE edrpou NOT IN(\n" +
                 "SELECT provider_fk\n" +
                 "FROM product p\n" +
                 "WHERE articul IN(\n" +
                    "SELECT product_articul\n" +
                    "FROM product_in_order pio\n" +
                    "WHERE order_id IN(\n" +
                        "SELECT id\n" +
                        "FROM order_t o\n" +
                        "WHERE salesman_id NOT IN(\n" +
                            "SELECT salesman_id\n" +
                            "FROM order_t ord\n" +
                            "WHERE order_id IN(\n" +
                                "SELECT order_id\n" +
                                "FROM product_in_order pino\n" +
                                "WHERE product_articul IN (\n" +
                                    "SELECT articul\n" +
                                    "FROM product prod\n" +
                                    "WHERE provider_fk = :providerEdrpou))))))\n" +
            "AND :quantity <= (SELECT COALESCE(SUM(prod_quantity),0)\n" +
                               "FROM product_in_order\n" +
                               "WHERE order_id IN(\n" +
                                    "SELECT order_id\n" +
                                    "FROM order_t\n" +
                                    "WHERE status = 'DONE'\n" +
                               "AND product_articul IN(\n" +
                                    "SELECT articul\n" +
                                    "FROM product\n" +
                                    "WHERE provider_fk = prov.edrpou)))\n" +
            "ORDER BY name", nativeQuery = true)
    Iterable<Provider> findHavingJustSalesmenOfProviderAndQuantityOfProductsSoldBigger(@Param("quantity") int quantity, @Param("providerEdrpou") Long providerEdrpou);

}