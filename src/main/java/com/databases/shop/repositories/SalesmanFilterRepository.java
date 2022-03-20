package com.databases.shop.repositories;

import com.databases.shop.models.Salesman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesmanFilterRepository {

    @Autowired
    private EntityManager entityManager;

    public Iterable<Salesman> filterSalesmen(double income, double orders, boolean hasAllCategories) {

        boolean ordersFilterEnabled = orders > 0;
        boolean incomeFilterEnabled = income > 0;

        String ordersFilter = ordersFilterEnabled ?
                "id IN (" +
                        "SELECT salesman_id\n" +
                        "FROM order_t\n" +
                        "    WHERE status = 'DONE'\n" +
                        "    GROUP BY salesman_id\n" +
                        "    HAVING COUNT(id) >= :orders)" : " TRUE ";

        String incomeFilter = incomeFilterEnabled ?
                "id IN (SELECT salesman_id\n" +
                        "    FROM\n" +
                        "        (SELECT salesman_id, prod_price*prod_quantity AS row_cost\n" +
                        "         FROM order_t INNER JOIN product_in_order pio ON order_t.id = pio.order_id\n" +
                        "         WHERE status = 'DONE') AS RowCosts\n" +
                        "    GROUP BY salesman_id\n" +
                        "    HAVING SUM(row_cost) >= :income)" : " TRUE ";

        String hasAllCategoriesFilter = hasAllCategories ?
                "NOT EXISTS (\n" +
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
                        "    )" : " TRUE ";

        Query query = entityManager.createNativeQuery(
                "SELECT * \n" +
                        "FROM salesman\n" +
                        "WHERE "
                        + ordersFilter
                        + " AND "
                        + incomeFilter
                        + " AND "
                        + hasAllCategoriesFilter,
                Salesman.class);
        if (incomeFilterEnabled) query.setParameter("income", income);
        if (ordersFilterEnabled) query.setParameter("orders", orders);
        List<Salesman> salesmen = new ArrayList<>();
        try {
            salesmen = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesmen;
    }


}
