package com.databases.shop.repositories;

import com.databases.shop.models.Salesman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesmanFilterRepository {

    @Autowired
    private EntityManager entityManager;

    public Iterable<Salesman> filterSalesmen(double income, double orders, boolean hasAllCategories) {

        String ordersFilter =
                "id IN (SELECT salesman.id\n" +
                        "FROM salesman\n" +
                        "WHERE :orders <= (\n" +
                        "    SELECT COUNT(*)\n" +
                        "    FROM order_t\n" +
                        "    WHERE status = 'DONE' AND salesman_id = salesman.id\n" +
                        "    ))";

        String incomeFilter =
                "id IN (SELECT salesman.id\n" +
                        "FROM salesman LEFT OUTER JOIN (SELECT salesman_id, prod_price*prod_quantity AS row_cost\n" +
                        "     FROM order_t INNER JOIN product_in_order pio ON order_t.id = pio.order_id\n" +
                        "     WHERE status = 'DONE') RowCosts ON salesman.id = RowCosts.salesman_id\n" +
                        "GROUP BY salesman.id\n" +
                        "HAVING SUM(COALESCE(row_cost,0)) >= :income)";

        String hasAllCategoriesFilter =
                "(:hasAllCategories = FALSE OR NOT EXISTS (\n" +
                        "        SELECT category.id\n" +
                        "        FROM category\n" +
                        "        WHERE NOT EXISTS(\n" +
                        "                SELECT articul\n" +
                        "                FROM product\n" +
                        "                WHERE category_fk = category.id AND EXISTS(\n" +
                        "                        SELECT product_articul\n" +
                        "                        FROM product_in_order INNER JOIN order_t ot ON product_in_order.order_id = ot.id\n" +
                        "                        WHERE status = 'DONE' AND product_articul = articul AND salesman_id = salesman.id\n" +
                        "                    )\n" +
                        "            )\n" +
                        "    ))";

        Query query = entityManager.createNativeQuery(
                "SELECT * \n" +
                        "FROM salesman\n" +
                        "WHERE "
                        + ordersFilter
                        + " AND "
                        + incomeFilter
                        + " AND "
                        + hasAllCategoriesFilter
                        +" ORDER BY person_surname",
                Salesman.class);
        query.setParameter("income", income);
        query.setParameter("orders", orders);
        query.setParameter("hasAllCategories", hasAllCategories);
        List<Salesman> salesmen = new ArrayList<>();
        try {
            salesmen = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesmen;
    }


}
