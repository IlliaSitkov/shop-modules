package com.databases.shop.repositories;

import com.databases.shop.models.Category;
import com.databases.shop.models.Product;
import com.databases.shop.repositories.queryinterfaces.MinMaxCustomersQuantity;
import com.databases.shop.repositories.queryinterfaces.MinMaxProductsQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE LOWER(c.name) LIKE LOWER(:name)")
    Iterable<Category> findByName(@Param("name") String name);

    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE LOWER(c.name) LIKE LOWER(:name) AND NOT c.id = :id")
    Iterable<Category> findByNameAndNotId(@Param("id") long id, @Param("name") String name);

    @Query(value =
            "SELECT COALESCE (MIN(products_quantity),0) AS minQuantity, COALESCE (MAX(products_quantity),0) AS maxQuantity\n" +
                    "FROM\n" +
                    "    (SELECT SUM(p_quantity) AS products_quantity\n" +
                    "    FROM\n" +
                    "        (SELECT category_fk, prod_quantity AS p_quantity\n" +
                    "        FROM product INNER JOIN product_in_order pio ON product.articul = pio.product_articul\n" +
                    "        WHERE order_id IN(\n" +
                    "                            SELECT id\n" +
                    "                            FROM order_t\n" +
                    "                            WHERE status = 'DONE')) AS PrQuantities\n" +
                    "    GROUP BY category_fk) AS ProductsQuantities", nativeQuery = true)
    MinMaxProductsQuantity minMaxProductsQuantity();

    @Query(value =
            "SELECT COALESCE (MIN(customers_quantity),0) AS minQuantity, COALESCE (MAX(customers_quantity),0) AS maxQuantity\n" +
                    "FROM\n" +
                    "    (SELECT COUNT(DISTINCT cus_id) AS customers_quantity\n" +
                    "    FROM\n" +
                    "        (SELECT category_fk, customer_id AS cus_id\n" +
                    "        FROM product INNER JOIN product_in_order pio ON product.articul = pio.product_articul\n" +
                    "              INNER JOIN order_t ON order_t.id = pio.order_id\n" +
                    "        WHERE status = 'DONE') AS CusIds\n" +
                    "    GROUP BY category_fk) AS CustomersIds", nativeQuery = true)
    MinMaxCustomersQuantity minMaxCustomersQuantity();

    @Query(value =
            "SELECT *\n" +
            "FROM category\n" +
            "WHERE :productsQuant <= (SELECT COALESCE(SUM(prod_quantity),0)\n" +
                                     "FROM product_in_order\n" +
                                     "WHERE order_id IN(\n" +
                                         "SELECT order_id\n" +
                                         "FROM order_t\n" +
                                         "WHERE status = 'DONE'\n" +
                                     "AND product_articul IN(\n" +
                                         "SELECT articul\n" +
                                         "FROM product\n" +
                                         "WHERE category_fk = id)))\n" +
            "AND :customersQuant <= (SELECT COALESCE (COUNT(DISTINCT customer_id),0)\n" +
                                    "FROM order_t\n" +
                                    "WHERE status = 'DONE' AND id IN(\n" +
                                        "SELECT order_id\n" +
                                        "FROM product_in_order\n" +
                                        "WHERE product_articul IN(\n" +
                                            "SELECT articul\n" +
                                            "FROM product\n" +
                                            "WHERE category_fk = id)))", nativeQuery = true)
    Iterable<Category> findHavingQuantityOfCustomersBiggerAndQuantityOfProductsSoldBigger(int customersQuant, int productsQuant);

    @Query(value =
            "SELECT *\n" +
            "FROM category\n" +
            "WHERE :productsQuant <= (SELECT COALESCE(SUM(prod_quantity),0)\n" +
                                     "FROM product_in_order\n" +
                                     "WHERE order_id IN(\n" +
                                        "SELECT order_id\n" +
                                        "FROM order_t\n" +
                                        "WHERE status = 'DONE'\n" +
                                     "AND product_articul IN(\n" +
                                        "SELECT articul\n" +
                                        "FROM product\n" +
                                        "WHERE category_fk = id)))\n" +
             "AND :customersQuant <= (SELECT COALESCE (COUNT(DISTINCT customer_id),0)\n" +
                                     "FROM order_t\n" +
                                     "WHERE status = 'DONE' AND id IN(\n" +
                                        "SELECT order_id\n" +
                                        "FROM product_in_order\n" +
                                        "WHERE product_articul IN(\n" +
                                            "SELECT articul\n" +
                                            "FROM product\n" +
                                            "WHERE category_fk = id)))" +
             "AND (SELECT COALESCE (COUNT(DISTINCT customer_id),0)\n" +
                  "FROM order_t\n" +
                  "WHERE status = 'DONE' AND id IN(\n" +
                    "SELECT order_id\n" +
                    "FROM product_in_order\n" +
                    "WHERE product_articul IN(\n" +
                        "SELECT articul\n" +
                        "FROM product\n" +
                        "WHERE category_fk = id))) = \n" +
                 "(SELECT COALESCE (MAX(customers_quantity),0)\n" +
                 "FROM\n" +
                    "(SELECT COUNT(DISTINCT cus_id) AS customers_quantity\n" +
                    "FROM\n" +
                        "(SELECT category_fk, customer_id AS cus_id\n" +
                        "FROM product INNER JOIN product_in_order pio ON product.articul = pio.product_articul\n" +
                             "INNER JOIN order_t ON order_t.id = pio.order_id\n" +
                        "WHERE status = 'DONE') AS CusIds\n" +
                        "GROUP BY category_fk) AS CustomersIds)", nativeQuery = true)
    Iterable<Category> findHavingQuantityOfCustomersBiggerAndQuantityOfProductsSoldBiggerWithMaxProductsQuantity(int customersQuant, int productsQuant);
}
