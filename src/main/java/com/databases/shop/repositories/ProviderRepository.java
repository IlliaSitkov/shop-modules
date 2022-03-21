package com.databases.shop.repositories;

import com.databases.shop.models.Category;
import com.databases.shop.models.Provider;
import com.databases.shop.repositories.queryinterfaces.MinMaxProductsQuantity;
import com.databases.shop.repositories.queryinterfaces.MinMaxSalesmanIncome;
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

    /*@Query(value =
            "SELECT *\n" +
            "FROM provider prov\n" +
            "WHERE edrpou NOT IN(\n" +
                "SELECT provider_fk\n" +
                "FROM product p\n" +
                "WHERE articul IN(\n" +
                    "SELECT product_articul\n" +
                    "FROM product_in_order pio\n" +
                    "WHERE order_id o IN(\n" +
                        "SELECT id\n" +
                        "FROM order_t\n" +
                        "WHERE salesman_id NOT IN(\n" +
                            "SELECT salesman_id\n" +
                            "FROM order_t\n" +
                            "WHERE order_id IN(\n" +
                                "SELECT order_id\n" +
                                "FROM product_in_order\n" +
                                "WHERE product_articul IN (\n" +
                                    "SELECT articul\n" +
                                    "FROM product\n" +
                                    "WHERE provider_fk IN(\n" +
                                        "SELECT edrpou\n" +
                                        "FROM provider\n" +
                                        "WHERE name = :name)))))))", nativeQuery = true)
    Iterable<Provider> findHavingAllSalesmenOfProvider(@Param("name") String name);*/

    @Query(value =
            "SELECT *\n" +
            "FROM provider\n" +
            "WHERE (SELECT SUM(prod_quantity)\n" +
                                "FROM product_in_order\n" +
                                "WHERE order_id IN(\n" +
                                    "SELECT order_id\n" +
                                    "FROM order_t\n" +
                                    "WHERE status = 'DONE'\n" +
                                "AND product_articul IN(\n" +
                                    "SELECT articul\n" +
                                    "FROM product\n" +
                                    "WHERE provider_fk = edrpou))) >= :quantity", nativeQuery = true)
    Iterable<Provider> findHavingQuantityOfProductsSoldBigger(@Param("quantity") int quantity);

    @Query(value =
            "SELECT *\n" +
                    "FROM provider\n" +
                    "WHERE name = :name ", nativeQuery = true)
    Iterable<Provider> findName(@Param("name") String name);

    @Query(value =
            "SELECT COALESCE (MIN(products_quantity),0) AS minQuantity, COALESCE (MAX(products_quantity),0) AS maxQuantity\n" +
                    "FROM\n" +
                    "    (SELECT SUM(p_quantity) AS products_quantity\n" +
                    "    FROM\n" +
                    "        (SELECT provider_fk, prod_quantity AS p_quantity\n" +
                    "        FROM product INNER JOIN product_in_order pio ON product.articul = pio.product_articul\n" +
                    "        WHERE order_id IN(\n" +
                    "                            SELECT id\n" +
                    "                            FROM order_t\n" +
                    "                            WHERE status = 'DONE')) AS PrQuantities\n" +
                    "    GROUP BY provider_fk) AS ProductsQuantities", nativeQuery = true)
    MinMaxProductsQuantity minMaxProductsQuantity();

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
                                    "WHERE provider_fk IN(\n" +
                                        "SELECT edrpou\n" +
                                        "FROM provider\n" +
                                        "WHERE name = :name)))))))\n" +
            "AND :quantity <= (SELECT SUM(prod_quantity)\n" +
                               "FROM product_in_order\n" +
                               "WHERE order_id IN(\n" +
                                    "SELECT order_id\n" +
                                    "FROM order_t\n" +
                                    "WHERE status = 'DONE'\n" +
                               "AND product_articul IN(\n" +
                                    "SELECT articul\n" +
                                    "FROM product\n" +
                                    "WHERE provider_fk = prov.edrpou)))", nativeQuery = true)
    Iterable<Provider> findHavingAllSalesmenOfProviderAndQuantityOfProductsSoldBigger(@Param("quantity") int quantity, @Param("name") String name);

}