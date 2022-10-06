package com.databases.shop.repositories;

import com.databases.shop.models.Customer;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c " +
            "FROM Customer c")
    Iterable<Customer> getAll();

//    @Query("SELECT case when count(c)> 0 then true else false end FROM Customer c WHERE c.contacts.email LIKE :email")
//    boolean existsByEmail(@Param("email") String email);

//my version
//    @Query(value =
//            "SELECT COALESCE(MIN(avgCost),0) AS minValue, COALESCE(MAX(avgCost),0) AS maxValue\n" +
//            "FROM (\n" +
//            "         SELECT AVG(COALESCE(order_cost,0)) AS avgCost\n" +
//            "         FROM (\n" +
//            "                  SELECT customer_id, COALESCE(SUM(prod_quantity * prod_price), 0) AS order_cost\n" +
//            "                  FROM order_t LEFT OUTER JOIN product_in_order ON order_id = order_t.id\n" +
//            "                  WHERE status = 'DONE'\n" +
//            "                  GROUP BY id, customer_id\n" +
//            "              ) T RIGHT OUTER JOIN customer ON id = T.customer_id\n" +
//            "         GROUP BY id\n" +
//            "     ) T1", nativeQuery = true)
//    MinMaxValues getMinMaxAvgOrderCost();



    @Query(value =
            "SELECT ROUND(COALESCE(MIN(COALESCE(CAST(avgCost AS numeric),0)),0),2) AS minValue, ROUND(COALESCE(MAX(COALESCE(CAST(avgCost AS numeric),0)),0),2) AS maxValue\n" +
                    "FROM customer LEFT OUTER JOIN\n" +
                    "    (\n" +
                    "        SELECT AVG(order_cost1) AS avgCost, customer_id\n" +
                    "        FROM (\n" +
                    "                 SELECT order_id, SUM(prod_quantity * prod_price) AS order_cost1\n" +
                    "                 FROM product_in_order\n" +
                    "                 GROUP BY order_id\n" +
                    "             ) OrderCost INNER JOIN order_t ON OrderCost.order_id = order_t.id\n" +
                    "        WHERE status = 'DONE'\n" +
                    "        GROUP BY customer_id\n" +
                    "    ) AllCosts ON customer.id = AllCosts.customer_id", nativeQuery = true)
    MinMaxValues getMinMaxAvgOrderCost();



    @Query(value =
            "SELECT COALESCE(MIN(COALESCE(AllQuants.prod_quant_customer,0)),0) AS minValue, COALESCE(MAX(COALESCE(AllQuants.prod_quant_customer,0)),0) AS maxValue\n" +
            "FROM customer LEFT OUTER JOIN (\n" +
            "         SELECT customer_id, SUM(prod_quantity) AS prod_quant_customer\n" +
            "         FROM order_t INNER JOIN product_in_order pio on order_t.id = pio.order_id\n" +
            "         WHERE status = 'DONE'\n" +
            "         GROUP BY customer_id) AllQuants ON customer.id = AllQuants.customer_id", nativeQuery = true)
    MinMaxValues getMinMaxOverallQuantity();


    @Query(value = "SELECT case when COUNT(*)> 0 then true else false end FROM customer WHERE contacts_email LIKE :email", nativeQuery = true)
    boolean existsByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM customer WHERE contacts_email LIKE :email", nativeQuery = true)
    Customer getByEmail(@Param("email") String email);
}
