package com.databases.shop.repositories;

import com.databases.shop.models.Category;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value =
            "SELECT *\n" +
            "FROM category\n" +
            "WHERE LOWER(name) LIKE LOWER(:name)", nativeQuery = true)
    Iterable<Category> findByName(String name);

    @Query(value =
            "SELECT *\n" +
            "FROM category\n" +
            "WHERE LOWER(name) LIKE LOWER(:name) AND NOT id = :id", nativeQuery = true)
    Iterable<Category> findByNameAndNotId(long id, String name);

    @Transactional
    @Modifying
    @Query(value =
            "DELETE FROM category\n" +
            "WHERE id = :id", nativeQuery = true)
    void deleteCById(long id);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE category\n" +
            "SET name = :name,\n" +
                "description = :description\n" +
            "WHERE id = :id", nativeQuery = true)
    void update(Long id, String name, String description);

    @Query(value =
            "SELECT COALESCE(MIN(COALESCE(products_quantity,0)),0) AS minValue, COALESCE(MAX(COALESCE(products_quantity,0)),0) AS maxValue\n" +
                    "FROM category LEFT OUTER JOIN (\n" +
                    "    SELECT category_fk, COUNT(*) AS products_quantity\n" +
                    "    FROM product\n" +
                    "    GROUP BY category_fk) AS ProductsQuantities ON category.id = category_fk", nativeQuery = true)
    MinMaxValues minMaxProductsQuantity();

    @Query(value =
            "SELECT COALESCE(MIN(COALESCE(customers_quantity,0)),0) AS minValue, COALESCE(MAX(COALESCE(customers_quantity,0)),0) AS maxValue\n" +
                    "FROM category LEFT OUTER JOIN (\n" +
                    "    SELECT category_fk, COUNT(DISTINCT customer_id) AS customers_quantity\n" +
                    "    FROM product INNER JOIN product_in_order pio ON product.articul = pio.product_articul\n" +
                    "              INNER JOIN order_t ON order_t.id = pio.order_id\n" +
                    "    WHERE status = 'DONE'\n" +
                    "    GROUP BY category_fk) AS CustomersIds ON category.id = category_fk", nativeQuery = true)
    MinMaxValues minMaxCustomersQuantity();

    @Query(value =
            "SELECT *\n" +
            "FROM category\n" +
            "WHERE :productsQuant <= (SELECT COALESCE(COUNT(*),0)\n" +
                                     "FROM product\n" +
                                     "WHERE category_fk = id)\n" +
            "AND :customersQuant <= (SELECT COALESCE (COUNT(DISTINCT customer_id),0)\n" +
                                    "FROM order_t\n" +
                                    "WHERE status = 'DONE' AND order_t.id IN(\n" +
                                        "SELECT order_id\n" +
                                        "FROM product_in_order\n" +
                                        "WHERE product_articul IN(\n" +
                                            "SELECT articul\n" +
                                            "FROM product\n" +
                                            "WHERE category_fk = category.id)))", nativeQuery = true)
    Iterable<Category> findHavingQuantityOfCustomersBiggerAndQuantityOfProductsSoldBigger(int customersQuant, int productsQuant);

    @Query(value =
            "SELECT *\n" +
            "FROM category\n" +
            "WHERE :productsQuant <= (SELECT COALESCE(COUNT(*),0)\n" +
                                     "FROM product\n" +
                                     "WHERE category_fk = id)\n" +
             "AND :customersQuant <= (SELECT COALESCE (COUNT(DISTINCT customer_id),0)\n" +
                                     "FROM order_t\n" +
                                     "WHERE status = 'DONE' AND order_t.id IN(\n" +
                                        "SELECT order_id\n" +
                                        "FROM product_in_order\n" +
                                        "WHERE product_articul IN(\n" +
                                            "SELECT articul\n" +
                                            "FROM product\n" +
                                            "WHERE category_fk = category.id)))" +
             "AND (SELECT COALESCE(SUM(prod_quantity),0)\n" +
                  "FROM product_in_order\n" +
                  "WHERE order_id IN(\n" +
                    "SELECT order_id\n" +
                    "FROM order_t\n" +
                    "WHERE status = 'DONE'\n" +
                    "AND product_articul IN(\n" +
                        "SELECT articul\n" +
                        "FROM product\n" +
                        "WHERE category_fk = category.id))) = \n" +
                    "(SELECT COALESCE(MAX(COALESCE(products_quantity,0)),0)\n" +
                    "FROM category LEFT OUTER JOIN (\n" +
                    "    SELECT category_fk, SUM(prod_quantity) AS products_quantity\n" +
                    "    FROM product INNER JOIN product_in_order pio ON product.articul = pio.product_articul\n" +
                    "         INNER JOIN order_t ON order_t.id = pio.order_id\n" +
                    "    WHERE status = 'DONE'\n" +
                    "    GROUP BY category_fk) AS ProductsQuantities ON category.id = category_fk)", nativeQuery = true)
    Iterable<Category> findHavingQuantityOfCustomersBiggerAndQuantityOfProductsSoldBiggerWithMaxProductsQuantity(int customersQuant, int productsQuant);
}
