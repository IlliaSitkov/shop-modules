package com.databases.shop.repositories;

import com.databases.shop.models.Order;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,Long> {


//    @Query(value =
//            "SELECT COALESCE(MIN(COALESCE(prodNum,0)),0) AS minValue, COALESCE(MAX(COALESCE(prodNum,0)),0) AS maxValue\n" +
//            "FROM order_t LEFT OUTER JOIN\n" +
//            "     (\n" +
//            "         SELECT order_id, COUNT(*) AS prodNum\n" +
//            "         FROM product_in_order\n" +
//            "         GROUP BY order_id\n" +
//            "     ) P ON order_t.id = P.order_id", nativeQuery = true)
//    MinMaxValues getMinMaxProdNameNumber();

    @Query(value =
            "SELECT COALESCE(MIN(COALESCE(prodNum,0)),0) AS minValue, COALESCE(MAX(COALESCE(prodNum,0)),0) AS maxValue\n" +
            "FROM order_t LEFT OUTER JOIN\n" +
            "     (\n" +
            "         SELECT order_id, COUNT(*) AS prodNum\n" +
            "         FROM product_in_order\n" +
            "         GROUP BY order_id\n" +
            "     ) P ON order_t.id = P.order_id\n" +
            "WHERE (:customerId < 0 OR  :customerId = customer_id)\n" +
            "  AND ((:customerId < 0 AND :salesmanId < 0) OR status <> 'NEW')\n" +
            "  AND (:salesmanId < 0 OR :salesmanId = salesman_id OR salesman_id IS NULL)", nativeQuery = true)
    MinMaxValues getMinMaxProdNameNumber(@Param("customerId") Long customerId, @Param("salesmanId") Long salesmanId);


//    @Query(value =
//            "SELECT COALESCE(MIN(COALESCE(catNum,0)),0) AS minValue, COALESCE(MAX(COALESCE(catNum,0)),0) AS maxValue\n" +
//            "FROM order_t LEFT OUTER JOIN (\n" +
//            "    SELECT order_id, COUNT(DISTINCT category_fk) AS catNum\n" +
//            "    FROM product_in_order INNER JOIN product on product_in_order.product_articul = product.articul\n" +
//            "    GROUP BY product_in_order.order_id\n" +
//            ") T ON order_t.id = T.order_id", nativeQuery = true)
//    MinMaxValues getMinMaxCategoryNumber();

    @Query(value =
            "SELECT MIN(COALESCE(catNum,0)) AS minValue, MAX(COALESCE(catNum,0)) AS maxValue\n" +
                    "FROM order_t LEFT OUTER JOIN (\n" +
                    "    SELECT order_id, COUNT(DISTINCT category_fk) AS catNum\n" +
                    "    FROM product_in_order INNER JOIN product on product_in_order.product_articul = product.articul\n" +
                    "    GROUP BY product_in_order.order_id\n" +
                    ") T ON order_t.id = T.order_id\n" +
                    "WHERE (:customerId < 0 OR  :customerId = customer_id)\n" +
                    "  AND ((:customerId < 0 AND :salesmanId < 0) OR status <> 'NEW')\n" +
                    "  AND (:salesmanId < 0 OR :salesmanId = salesman_id OR salesman_id IS NULL)", nativeQuery = true)
    MinMaxValues getMinMaxCategoryNumber(@Param("customerId") Long customerId, @Param("salesmanId") Long salesmanId);


//    @Query(value =
//            "SELECT COALESCE(MIN(COALESCE(ordCost,0)),0) AS minValue, COALESCE(MAX(COALESCE(ordCost,0)),0) AS maxValue\n" +
//            "FROM order_t LEFT OUTER JOIN (\n" +
//            "    SELECT order_id, SUM(prod_quantity*prod_price) AS ordCost\n" +
//            "    FROM product_in_order\n" +
//            "    GROUP BY order_id\n" +
//            ") T ON order_t.id = T.order_id", nativeQuery = true)
//    MinMaxValues getMinMaxCost();

    @Query(value =
            "SELECT MIN(COALESCE(ordCost,0)) AS minValue, MAX(COALESCE(ordCost,0)) AS maxValue\n" +
            "FROM order_t LEFT OUTER JOIN (\n" +
            "    SELECT order_id, SUM(prod_quantity*prod_price) AS ordCost\n" +
            "    FROM product_in_order\n" +
            "    GROUP BY order_id\n" +
            ") T ON order_t.id = T.order_id\n" +
            "WHERE (:customerId < 0 OR  :customerId = customer_id)\n" +
            "  AND ((:customerId < 0 AND :salesmanId < 0) OR status <> 'NEW')\n" +
            "  AND (:salesmanId < 0 OR :salesmanId = salesman_id OR salesman_id IS NULL)", nativeQuery = true)
    MinMaxValues getMinMaxCost(@Param("customerId") Long customerId, @Param("salesmanId") Long salesmanId);


    @Query(value = "SELECT * FROM order_t",nativeQuery = true)
    Iterable<Order> getAll();


    @Query(value =
            "SELECT * " +
            "FROM order_t " +
            "WHERE status = 'NEW' AND customer_id IN (" +
                    "SELECT customer.id " +
                    "FROM customer " +
                    "WHERE contacts_email LIKE :email)",nativeQuery = true)
    Order getByCustomerEmail(@Param("email") String email);

}
