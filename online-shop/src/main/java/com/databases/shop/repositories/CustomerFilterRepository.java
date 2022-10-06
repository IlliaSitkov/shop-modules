package com.databases.shop.repositories;

import com.databases.shop.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerFilterRepository {

    @Autowired
    private EntityManager entityManager;

    public Iterable<Customer> filterCustomers(int overallProdQuant, int avgOrderCost, long customerId, long productId, int boughtTimes) {

        String productMoreThanKFilter =
                "(:prodId < 0 OR id IN (\n" +
                        "SELECT customer.id\n" +
                        "FROM customer\n" +
                        "WHERE :prodQuant = (\n" +
                        "    SELECT COUNT(*)\n" +
                        "    FROM order_t INNER JOIN product_in_order pio ON order_t.id = pio.order_id\n" +
                        "    WHERE customer_id = customer.id AND order_t.status = 'DONE' AND product_articul = :prodId\n" +
                        "    )" +
                        "))";

        String avgOrderCostFilter =
                "id IN (\n" +
                        "SELECT id\n" +
                        "FROM customer\n" +
                        "WHERE :avgCost <= (\n" +
                        "    SELECT COALESCE(AVG(orderCost),0)\n" +
                        "    FROM (\n" +
                        "             SELECT order_id, SUM(prod_quantity*prod_price) AS orderCost\n" +
                        "             FROM product_in_order\n" +
                        "             GROUP BY order_id\n" +
                        "         ) OrderCost INNER JOIN order_t ON OrderCost.order_id = order_t.id\n" +
                        "    WHERE order_t.status = 'DONE' AND order_t.customer_id = customer.id)\n" +
                "    )";

        String overallProdQuantFilter =
                "id IN (\n" +
                        "SELECT id\n" +
                        "        FROM customer\n" +
                        "        WHERE :overallQuant <= (\n" +
                        "            SELECT COALESCE(SUM(prod_quantity),0)\n" +
                        "            FROM order_t INNER JOIN product_in_order pio on order_t.id = pio.order_id\n" +
                        "            WHERE customer_id = customer.id AND status = 'DONE'\n" +
                        "        )\n" +
                        "    )";

        String hasAllOnlyCategoriesFilter =
                "(:customerId < 0 OR NOT EXISTS(\n" +
                        "SELECT *\n" +
                        "FROM customer\n" +
                        "WHERE NOT EXISTS(\n" +
                        "        SELECT category_fk\n" +
                        "        FROM ((product INNER JOIN product_in_order ON product.articul = product_articul)\n" +
                        "            INNER JOIN order_t ON product_in_order.order_id = order_t.id) CPRO\n" +
                        "        WHERE CPRO.customer_id = :customerId AND CPRO.status = 'DONE' AND NOT EXISTS(\n" +
                        "                SELECT *\n" +
                        "                FROM ((product INNER JOIN product_in_order ON product.articul = product_articul)\n" +
                        "                    INNER JOIN order_t ON product_in_order.order_id = order_t.id) CPRO1\n" +
                        "                WHERE CPRO1.category_fk = CPRO.category_fk AND CPRO1.status = 'DONE' AND CPRO1.customer_id = customer.id\n" +
                        "                )\n" +
                        "    ) AND NOT EXISTS (\n" +
                        "    SELECT category_fk\n" +
                        "    FROM ((product INNER JOIN product_in_order ON product.articul = product_articul)\n" +
                        "        INNER JOIN order_t ON product_in_order.order_id = order_t.id) CPRO\n" +
                        "    WHERE CPRO.status = 'DONE' AND CPRO.customer_id = customer.id AND category_fk NOT IN (\n" +
                        "        SELECT CPRO1.category_fk\n" +
                        "        FROM ((product INNER JOIN product_in_order ON product.articul = product_articul)\n" +
                        "            INNER JOIN order_t ON product_in_order.order_id = order_t.id) CPRO1\n" +
                        "        WHERE CPRO1.status = 'DONE' AND CPRO1.customer_id = :customerId\n" +
                        "    )\n" +
                        ")))";

        Query query = entityManager.createNativeQuery(
                "SELECT *\n" +
                        "FROM customer\n" +
                        "WHERE "
                        + hasAllOnlyCategoriesFilter
                        + " AND " + avgOrderCostFilter
                        + " AND " + overallProdQuantFilter
                        + " AND " + productMoreThanKFilter
                        + " ORDER BY person_surname",Customer.class);

        query.setParameter("prodQuant",boughtTimes);
        query.setParameter("prodId",productId);

        query.setParameter("avgCost",avgOrderCost);
        query.setParameter("overallQuant",overallProdQuant);

        query.setParameter("customerId",customerId);

        List<Customer> customers = new ArrayList<>();
        try {
            customers = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }



}
