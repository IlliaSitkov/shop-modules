package com.databases.shop.repositories;

import com.databases.shop.models.Customer;
import com.databases.shop.repositories.queryinterfaces.MinMaxAvgOrderCost;
import com.databases.shop.repositories.queryinterfaces.MinMaxOverallQuantity;
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
            "SELECT MIN(COALESCE(cost,0)) AS minAvgOrderCost, MAX(COALESCE(cost,0)) AS maxAvgOrderCost\n" +
            "FROM customer LEFT OUTER JOIN (\n" +
            "    SELECT AVG(orderCost) AS cost, AvgCost.customer_id\n" +
            "    FROM ((\n" +
            "        SELECT Rows.order_id, SUM(Rows.row_cost) AS orderCost\n" +
            "        FROM (\n" +
            "                 SELECT order_id, prod_quantity*prod_price AS row_cost\n" +
            "                 FROM product_in_order\n" +
            "             ) Rows\n" +
            "        GROUP BY Rows.order_id\n" +
            "    ) OrderCost INNER JOIN order_t ON OrderCost.order_id = order_t.id) AvgCost\n" +
            "    WHERE AvgCost.status = 'DONE'\n" +
            "    GROUP BY AvgCost.customer_id) AllCosts ON customer.id = AllCosts.customer_id", nativeQuery = true)
    MinMaxAvgOrderCost getMinMaxAvgOrderCost();


    @Query(value =
            "SELECT MIN(COALESCE(AllQuants.prod_quant_customer,0)) AS minOverallQuantity, MAX(COALESCE(AllQuants.prod_quant_customer,0)) AS maxOverallQuantity\n" +
            "FROM customer LEFT OUTER JOIN (\n" +
            "         SELECT customer_id, SUM(prod_quantity) AS prod_quant_customer\n" +
            "         FROM order_t INNER JOIN product_in_order pio on order_t.id = pio.order_id\n" +
            "         WHERE status = 'DONE'\n" +
            "         GROUP BY customer_id) AllQuants ON customer.id = AllQuants.customer_id", nativeQuery = true)
    MinMaxOverallQuantity getMinMaxOverallQuantity();





}
