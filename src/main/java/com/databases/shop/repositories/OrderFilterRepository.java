package com.databases.shop.repositories;

import com.databases.shop.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
public class OrderFilterRepository {

    @Autowired
    private EntityManager entityManager;

    public Iterable<Order> filterOrders(List<String> statuses,
                                        int prodNameNum,
                                        int categNum,
                                        double orderCost,
                                        String date,
                                        boolean dateFilterEnabled,
                                        long salesmanId,
                                        long customerId,
                                        long providerIdBad,
                                        long orderId,
                                        long providerId,
                                        int prodNumK) {



        String statusFilter =
                "id IN ( -- 1. фільтр статусу замовлення\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE status IN :statuses\n" +
                        "    )";

        String prodNumFilter =
                "id IN ( -- 2. к-сть найменув. товарів >= вказаній\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE :prodNameNum <= (\n" +
                        "        SELECT COUNT(*)\n" +
                        "        FROM product_in_order\n" +
                        "        WHERE product_in_order.order_id = order_t.id)\n" +
                        "\n" +
                        "    )";

        String categoryNumFilter =
                "id IN ( -- 3. кількість категорій у замовленні більша або дорівнює вказаній\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE :categNum <= (\n" +
                        "        SELECT COUNT(DISTINCT category_fk)\n" +
                        "        FROM product_in_order INNER JOIN product ON product_in_order.product_articul = product.articul\n" +
                        "        WHERE product_in_order.order_id = order_t.id\n" +
                        "    )\n" +
                        "    )";

        String costFilter =
                "id IN ( -- 4. вартість замовлення більше дорівнює вказаній\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE :ordCost <= (\n" +
                        "        SELECT COALESCE(SUM(prod_quantity*prod_price),0)\n" +
                        "        FROM product_in_order\n" +
                        "        WHERE order_id = order_t.id\n" +
                        "    )\n" +
                        "    )";

        String dateFilter =
                "(:dateFilterEnabled = FALSE OR id IN (\n" +
                        "    -- 5. дата створення замовлення збігається із вказаною\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE date_created = :mDate\n" +
                        "    ))";

        String salesmanFilter =
                "(:salesmanId = -1 OR id IN (\n" +
                        "    -- 6. фільтр за продавцем\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE (:salesmanId = -2 AND salesman_id IS NULL) OR :salesmanId = salesman_id\n" +
                        "    )\n" +
                        "    )";

        String customerFilter =
                "(:customerId < 0 OR id IN (\n" +
                        "    -- 8. фільтр за покупцем\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE customer_id = :customerId\n" +
                        "    )\n" +
                        "    )";

        String noProviderFilter =
                "(:providerIdBad < 0 OR id IN (\n" +
                        "    -- 7. у замовленні немає товарів вказаного постачальника\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE id NOT IN (\n" +
                        "        SELECT order_id\n" +
                        "        FROM product_in_order INNER JOIN product ON product_in_order.product_articul = product.articul\n" +
                        "        WHERE provider_fk = :providerIdBad\n" +
                        "    )\n" +
                        "    )\n" +
                        "    )";

        String hasAllNotThoseProductsFilter =
                "(:orderId < 0 OR id IN (\n" +
                        "    -- 9. замовлення має усі не ті товари, що є у вказаному замовленні\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE NOT EXISTS(\n" +
                        "            SELECT *\n" +
                        "            FROM product\n" +
                        "            WHERE articul NOT IN (\n" +
                        "                SELECT product_articul\n" +
                        "                FROM product_in_order\n" +
                        "                WHERE order_id = :orderId\n" +
                        "            )\n" +
                        "              AND NOT EXISTS(\n" +
                        "                    SELECT *\n" +
                        "                    FROM product_in_order\n" +
                        "                    WHERE product_articul = articul\n" +
                        "                      AND order_id = id\n" +
                        "                )\n" +
                        "        )\n" +
                        "    )\n" +
                        "    )";

        String moreThenKProviderProds =
                "(:providerId < 0 OR id IN (\n" +
                        "    -- 10. замовлення містить К товарів вказаного виробника\n" +
                        "    SELECT id\n" +
                        "    FROM order_t\n" +
                        "    WHERE :prodNumK = (\n" +
                        "        SELECT COUNT(*)\n" +
                        "        FROM product_in_order INNER JOIN product ON product_in_order.product_articul = product.articul\n" +
                        "        WHERE provider_fk = :providerId AND order_id = id\n" +
                        "    )\n" +
                        "    ))";


        Query query = entityManager.createNativeQuery(
                "SELECT *\n" +
                        "FROM order_t\n" +
                        "WHERE\n"
                        + statusFilter + " AND "
                        + prodNumFilter + " AND "
                        + categoryNumFilter + " AND "
                        + costFilter + " AND "
                        + dateFilter + " AND "
                        + salesmanFilter + " AND "
                        + customerFilter + " AND "
                        + noProviderFilter + " AND "
                        + hasAllNotThoseProductsFilter + " AND "
                        + moreThenKProviderProds, Order.class);

        LocalDate dateObj = LocalDate.parse(date);

        query.setParameter("statuses",statuses);
        query.setParameter("prodNameNum",prodNameNum);
        query.setParameter("categNum",categNum);
        query.setParameter("ordCost",orderCost);
        query.setParameter("mDate",dateObj);
        query.setParameter("dateFilterEnabled",dateFilterEnabled);
        query.setParameter("salesmanId",salesmanId);
        query.setParameter("customerId",customerId);
        query.setParameter("providerIdBad",providerIdBad);
        query.setParameter("orderId",orderId);
        query.setParameter("providerId",providerId);
        query.setParameter("prodNumK",prodNumK);

        List<Order> orders = new ArrayList<>();
        try {
            orders = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }







}
