package com.databases.shop.repositories;

import com.databases.shop.models.Customer;
import com.databases.shop.models.Salesman;
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

        boolean hasAllOnlyCategoriesFilterEnabled = customerId >= 0;

        boolean productMoreThanKFilterEnabled = productId >= 0 && boughtTimes > 0;

        String productMoreThanKFilter = productMoreThanKFilterEnabled ?
                "id IN (\n" +
                "        SELECT customer_id\n" +
                        "        FROM order_t INNER JOIN product_in_order pio ON order_t.id = pio.order_id\n" +
                        "        WHERE order_t.status = 'DONE' AND product_articul = :prodId\n" +
                        "        GROUP BY customer_id\n" +
                        "        HAVING COUNT(*) >= :prodQuant\n" +
                        "    )" : " TRUE ";

        String avgOrderCostFilter =
                "id IN (\n" +
                "        SELECT id\n" +
                "        FROM customer\n" +
                "        WHERE :avgCost <= (\n" +
                "            SELECT COALESCE(AVG(orderCost),0)\n" +
                "            FROM (\n" +
                "                     SELECT Rows.order_id, SUM(Rows.row_cost) AS orderCost\n" +
                "                     FROM (SELECT order_id, prod_quantity*prod_price AS row_cost\n" +
                "                           FROM product_in_order) Rows\n" +
                "                     GROUP BY Rows.order_id\n" +
                "                 ) OrderCost INNER JOIN order_t ON OrderCost.order_id = order_t.id\n" +
                "            WHERE order_t.status = 'DONE' AND order_t.customer_id = customer.id\n" +
                "        )\n" +
                "    )";

        String overallProdQuantFilter =
                "id IN (\n" +
                "        SELECT id\n" +
                        "        FROM customer\n" +
                        "        WHERE :overallQuant <= (\n" +
                        "            SELECT COALESCE(SUM(prod_quantity),0)\n" +
                        "            FROM order_t INNER JOIN product_in_order pio on order_t.id = pio.order_id\n" +
                        "            WHERE customer_id = customer.id AND status = 'DONE'\n" +
                        "        )\n" +
                        "    )";

        String hasAllOnlyCategoriesFilter = hasAllOnlyCategoriesFilterEnabled ?
                "NOT EXISTS(\n" +
                "        SELECT *\n" +
                        "        FROM (((category INNER JOIN product p on category.id = p.category_fk)\n" +
                        "            INNER JOIN product_in_order ON p.articul = product_articul)\n" +
                        "            INNER JOIN order_t ON product_in_order.order_id = order_t.id) CPRO\n" +
                        "        WHERE CPRO.customer_id = :customerId AND CPRO.status = 'DONE' AND NOT EXISTS(\n" +
                        "                SELECT *\n" +
                        "                FROM product P\n" +
                        "                WHERE P.category_fk = CPRO.category_fk AND EXISTS(\n" +
                        "                        SELECT *\n" +
                        "                        FROM (product_in_order INNER JOIN order_t OT ON product_in_order.order_id = OT.id) PO\n" +
                        "                        WHERE PO.status = 'DONE' AND PO.product_articul = P.articul AND PO.customer_id = customer.id\n" +
                        "                    )\n" +
                        "            )\n" +
                        "    ) AND customer.id NOT IN (\n" +
                        "        SELECT CPRO1.customer_id\n" +
                        "        FROM (((category INNER JOIN product p2 ON id = p2.category_fk)\n" +
                        "            INNER JOIN product_in_order pio ON p2.articul = pio.product_articul)\n" +
                        "            INNER JOIN order_t ON pio.order_id = order_t.id) CPRO1\n" +
                        "        WHERE CPRO1.status = 'DONE' AND CPRO1.category_fk NOT IN (\n" +
                        "            SELECT CPRO2.category_fk\n" +
                        "            FROM (((category INNER JOIN product p ON category.id = p.category_fk)\n" +
                        "                INNER JOIN product_in_order ON p.articul = product_in_order.product_articul)\n" +
                        "                INNER JOIN order_t ON product_in_order.order_id = order_t.id) CPRO2\n" +
                        "            WHERE CPRO2.status = 'DONE' AND CPRO2.customer_id = :customerId\n" +
                        "        )\n" +
                        "    )": " TRUE ";

        Query query = entityManager.createNativeQuery(
                "SELECT *\n" +
                        "FROM customer\n" +
                        "WHERE "
                        + hasAllOnlyCategoriesFilter
                        + " AND " + avgOrderCostFilter
                        + " AND " + overallProdQuantFilter
                        + " AND " + productMoreThanKFilter
                        + " ORDER BY person_surname",Customer.class);

        if (productMoreThanKFilterEnabled) {
            query.setParameter("prodQuant",boughtTimes);
            query.setParameter("prodId",productId);
        }
        query.setParameter("avgCost",avgOrderCost);
        query.setParameter("overallQuant",overallProdQuant);
        if (hasAllOnlyCategoriesFilterEnabled) {
            query.setParameter("customerId",customerId);
        }

        List<Customer> customers = new ArrayList<>();
        try {
            customers = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }



}
