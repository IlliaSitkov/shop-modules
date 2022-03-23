package com.databases.shop.repositories;

import com.databases.shop.models.Order;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,Long> {


    @Query(value =
            "SELECT MIN(COALESCE(prodNum,0)) AS minValue, MAX(COALESCE(prodNum,0)) AS maxValue\n" +
            "FROM order_t LEFT OUTER JOIN\n" +
            "     (\n" +
            "         SELECT order_id, COUNT(*) AS prodNum\n" +
            "         FROM product_in_order\n" +
            "         GROUP BY order_id\n" +
            "     ) P ON order_t.id = P.order_id", nativeQuery = true)
    MinMaxValues getMinMaxProdNameNumber();


    @Query(value =
            "SELECT MIN(COALESCE(catNum,0)) AS minValue, MAX(COALESCE(catNum,0)) AS maxValue\n" +
            "FROM order_t LEFT OUTER JOIN (\n" +
            "    SELECT order_id, COUNT(DISTINCT category_fk) AS catNum\n" +
            "    FROM product_in_order INNER JOIN product on product_in_order.product_articul = product.articul\n" +
            "    GROUP BY product_in_order.order_id\n" +
            ") T ON order_t.id = T.order_id", nativeQuery = true)
    MinMaxValues getMinMaxCategoryNumber();


    @Query(value =
            "SELECT MIN(COALESCE(ordCost,0)) AS minValue, MAX(COALESCE(ordCost,0)) AS maxValue\n" +
            "FROM order_t LEFT OUTER JOIN (\n" +
            "    SELECT order_id, SUM(prod_quantity*prod_price) AS ordCost\n" +
            "    FROM product_in_order\n" +
            "    GROUP BY order_id\n" +
            ") T ON order_t.id = T.order_id", nativeQuery = true)
    MinMaxValues getMinMaxCost();


    @Query(value = "SELECT * FROM order_t",nativeQuery = true)
    Iterable<Order> getAll();
}
