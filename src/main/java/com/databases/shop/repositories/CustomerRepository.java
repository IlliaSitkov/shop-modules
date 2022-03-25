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


    @Query(value =
            "SELECT COALESCE(MIN(COALESCE(avgCost,0)),0) AS minValue, COALESCE(MAX(COALESCE(avgCost,0)),0) AS maxValue\n" +
            "FROM customer LEFT OUTER JOIN\n" +
            "    (\n" +
            "        SELECT AVG(order_cost) AS avgCost, customer_id\n" +
            "        FROM (\n" +
            "                 SELECT order_id, SUM(prod_quantity * prod_price) AS order_cost\n" +
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
