package com.databases.shop.repositories;

import com.databases.shop.models.Customer;
import com.databases.shop.repositories.queryinterfaces.MaxAvgOrderCost;
import com.databases.shop.repositories.queryinterfaces.MaxOverallQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c " +
            "FROM Customer c")
    Iterable<Customer> getAll();

//    @Query("SELECT case when count(c)> 0 then true else false end FROM Customer c WHERE c.contacts.email LIKE :email")
//    boolean existsByEmail(@Param("email") String email);


    @Query(value =
            "SELECT COALESCE(MAX(cost),0) AS maxAvgOrderCost\n" +
            "FROM (SELECT AVG(orderCost) AS cost\n" +
            "      FROM ((\n" +
            "          SELECT Rows.order_id, SUM(Rows.row_cost) AS orderCost\n" +
            "          FROM (\n" +
            "                   SELECT order_id, prod_quantity*prod_price AS row_cost\n" +
            "                   FROM product_in_order\n" +
            "               ) Rows\n" +
            "          GROUP BY Rows.order_id\n" +
            "      ) OrderCost INNER JOIN order_t ON OrderCost.order_id = order_t.id) AS AvgCost\n" +
            "      WHERE AvgCost.status = 'DONE'\n" +
            "      GROUP BY AvgCost.customer_id) AS AllCosts", nativeQuery = true)
    MaxAvgOrderCost getMaxAvgOrderCost();


    @Query(value =
            "SELECT COALESCE(MAX(AllQuants.prod_quant_customer),0) AS maxOverallQuantity\n" +
            "FROM (\n" +
            "         SELECT SUM(prod_quantity) AS prod_quant_customer\n" +
            "         FROM order_t INNER JOIN product_in_order pio on order_t.id = pio.order_id\n" +
            "         WHERE status = 'DONE'\n" +
            "         GROUP BY customer_id) AllQuants", nativeQuery = true)
    MaxOverallQuantity getMaxOverallQuantity();





}
