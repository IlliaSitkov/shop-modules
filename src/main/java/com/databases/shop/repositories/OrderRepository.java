package com.databases.shop.repositories;

import com.databases.shop.models.Order;
import com.databases.shop.models.OrderStatus;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.repositories.queryinterfaces.OrderGroupReportValues;
import com.databases.shop.repositories.queryinterfaces.OrderReportValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
            "SELECT COALESCE(MIN(prodNum),0) AS minValue, COALESCE(MAX(prodNum),0) AS maxValue\n" +
            "FROM (SELECT COUNT(product_articul) AS prodNum\n" +
            "FROM order_t LEFT OUTER JOIN product_in_order ON order_t.id = product_in_order.order_id\n" +
            "WHERE (:customerId < 0 OR  :customerId = customer_id)\n" +
            "    AND ((:customerId < 0 AND :salesmanId < 0) OR status <> 'NEW')\n" +
            "    AND (:salesmanId < 0 OR :salesmanId = salesman_id OR salesman_id IS NULL)\n" +
            "GROUP BY id) T", nativeQuery = true)
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
            "SELECT COALESCE(MIN(catNum),0) AS minValue, COALESCE(MAX(catNum),0) AS maxValue\n" +
            "FROM (SELECT COUNT(DISTINCT category_fk) AS catNum\n" +
            "      FROM (product_in_order INNER JOIN product on product_in_order.product_articul = product.articul)\n" +
            "               RIGHT OUTER JOIN order_t ON order_id = id\n" +
            "      WHERE (:customerId < 0 OR  :customerId = customer_id)\n" +
            "        AND ((:customerId < 0 AND :salesmanId < 0) OR status <> 'NEW')\n" +
            "        AND (:salesmanId < 0 OR :salesmanId = salesman_id OR salesman_id IS NULL)\n" +
            "      GROUP BY id) T", nativeQuery = true)
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
            "SELECT ROUND(COALESCE(MIN(CAST(ordCost AS numeric)),0),2) AS minValue, ROUND(COALESCE(MAX(CAST(ordCost AS numeric)),0),2) AS maxValue\n" +
            "FROM (SELECT COALESCE(SUM(prod_quantity*prod_price),0) AS ordCost\n" +
            "      FROM order_t INNER JOIN product_in_order ON order_t.id = product_in_order.order_id\n" +
            "      WHERE (:customerId < 0 OR  :customerId = customer_id)\n" +
            "        AND ((:customerId < 0 AND :salesmanId < 0) OR status <> 'NEW')\n" +
            "        AND (:salesmanId < 0 OR :salesmanId = salesman_id OR salesman_id IS NULL)\n" +
            "      GROUP BY id) T", nativeQuery = true)
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

    @Query(value =
            "SELECT * " +
            "FROM order_t " +
            "WHERE customer_id = :customerId",nativeQuery = true)
    Iterable<Order> findByCustomerId(@Param("customerId") Long customerId);


    @Query(value =
            "SELECT id AS orderId,\n" +
            "       COUNT(product_articul) AS productNamesNum,\n" +
            "       COUNT(DISTINCT category_fk) AS categoriesNum,\n" +
            "       COUNT(DISTINCT provider_fk) AS providersNum,\n" +
            "       ROUND(SUM(CAST(prod_quantity*prod_price AS numeric)),2) AS cost,\n" +
            "       SUM(prod_quantity) AS overallProductsNum,\n" +
            "       ROUND(AVG(CAST(prod_price AS numeric)),2) AS avgProductPrice,\n" +
            "       date_created AS dateDone,\n" +
            "       status AS orderStatus\n" +
            "FROM (product_in_order INNER JOIN product ON product_articul = articul)\n" +
            "    INNER JOIN order_t ON id = order_id\n" +
            "    WHERE date_created BETWEEN :dateStart AND :dateEnd\n" +
//            "WHERE date_created BETWEEN :dateStart AND :dateEnd\n" +
            "GROUP BY id\n" +
            "ORDER BY id", nativeQuery = true)
    Iterable<OrderReportValues> getOrderReportValues(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);


    @Query(value =
            "SELECT ROUND(COALESCE(SUM(CAST(prod_price*prod_quantity AS numeric)),0),2) AS income\n" +
            "FROM order_t INNER JOIN product_in_order ON id = order_id\n" +
            "WHERE status = 'DONE' AND date_created BETWEEN :dateStart AND :dateEnd", nativeQuery = true)
    double getFullIncome(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);


    @Query(value =
            "SELECT COUNT(DISTINCT id) AS orderNum\n" +
            "FROM order_t INNER JOIN product_in_order ON id = order_id\n" +
            "WHERE date_created BETWEEN :dateStart AND :dateEnd", nativeQuery = true)
    int getOrdersNum(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);


    @Query(value =
            "SELECT ROUND(COALESCE(AVG(CAST(orderCost AS numeric)),0),2) AS avgOrderCost\n" +
            "FROM (\n" +
            "         SELECT SUM(prod_quantity*prod_price) AS orderCost\n" +
            "         FROM order_t INNER JOIN product_in_order ON id = order_id\n" +
            "         WHERE date_created BETWEEN :dateStart AND :dateEnd\n" +
            "         GROUP BY id\n" +
            "     ) T", nativeQuery = true)
    int getAvgOrderCost(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);


    @Query(value =
            "SELECT status, COUNT(*) AS orderNum, ROUND(COALESCE(SUM(CAST(orderCost AS numeric)),0),2) AS orderGroupCost, ROUND(COALESCE(AVG(CAST(orderCost AS numeric)),0),2) AS avgOrderCost\n" +
            "FROM (\n" +
            "         SELECT status, SUM(prod_quantity*prod_price) AS orderCost\n" +
            "         FROM order_t INNER JOIN product_in_order ON id = order_id\n" +
            "         WHERE date_created BETWEEN :dateStart AND :dateEnd\n" +
            "         GROUP BY id, status\n" +
            "     ) T\n" +
            "GROUP BY status\n" +
            "ORDER BY status", nativeQuery = true)
    Iterable<OrderGroupReportValues> getOrderGroupReportValues(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);


    @Query(value =
            "SELECT COALESCE(MIN(date_created),'1970-01-01 00:00:00.000000')\n" +
            "FROM order_t INNER JOIN product_in_order ON id = order_id", nativeQuery = true)
    Date getMinDate();

    @Query(value =
            "SELECT COALESCE(MAX(date_created),'1970-01-01 00:00:00.000000')\n" +
                    "FROM order_t INNER JOIN product_in_order ON id = order_id", nativeQuery = true)
    Date getMaxDate();



}







