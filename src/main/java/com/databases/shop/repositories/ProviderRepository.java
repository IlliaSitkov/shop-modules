package com.databases.shop.repositories;

import com.databases.shop.models.Provider;
import com.databases.shop.repositories.queryinterfaces.MinMaxProductsQuantity;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
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
                                    "WHERE provider_fk = edrpou)))", nativeQuery = true)
    Iterable<Provider> findHavingQuantityOfProductsSoldBigger(@Param("quantity") int quantity);

    @Query(value =
            "SELECT *\n" +
                    "FROM provider\n" +
                    "WHERE name = :name ", nativeQuery = true)
    Iterable<Provider> findName(@Param("name") String name);

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
                                    "WHERE provider_fk = prov.edrpou)))", nativeQuery = true)
    Iterable<Provider> findHavingJustSalesmenOfProviderAndQuantityOfProductsSoldBigger(@Param("quantity") int quantity, @Param("providerEdrpou") Long providerEdrpou);

}