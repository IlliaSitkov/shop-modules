package com.databases.shop.repositories;

import com.databases.shop.models.Salesman;
import com.databases.shop.repositories.queryinterfaces.MinMaxOrderCount;
import com.databases.shop.repositories.queryinterfaces.MinMaxSalesmanIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesmanRepository extends JpaRepository<Salesman,Long> {

    @Query("SELECT s FROM Salesman s ORDER BY s.personName.surname")
    Iterable<Salesman> getAll();

    @Query("SELECT case when count(s)> 0 then true else false end FROM Salesman s WHERE s.contacts.email LIKE :email AND s.id = :id")
    boolean existsByEmailAndNotId(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT case when count(s)> 0 then true else false end FROM Salesman s WHERE s.contacts.email LIKE :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'DONE' AND o.salesman.id = :id")
    int salesmanOrderQuantity(@Param("id") Long id);

    @Query("SELECT s FROM Salesman s WHERE :quantity <= (SELECT COUNT(o) FROM Order o WHERE o.status = 'DONE' AND o.salesman.id = s.id)")
    int haveGEDoneOrderQuantity(@Param("quantity") int quantity);


    @Query(value =
            "SELECT COALESCE(MIN(num_ord),0) AS minCount, COALESCE(MAX(num_ord),0) AS maxCount\n" +
            "FROM (\n" +
            "    SELECT COUNT(*) AS num_ord\n" +
            "    FROM order_t\n" +
            "    WHERE status = 'DONE'\n" +
            "    GROUP BY salesman_id) AS T", nativeQuery = true)
    MinMaxOrderCount minMaxOrderCount();


    @Query(value =
            "SELECT COALESCE (MIN(sum_per_salesman),0) AS minIncome, COALESCE (MAX(sum_per_salesman),0) AS maxIncome\n" +
            "FROM\n" +
            "    (SELECT SUM(row_cost) AS sum_per_salesman\n" +
            "    FROM\n" +
            "        (SELECT salesman_id, prod_price*prod_quantity AS row_cost\n" +
            "        FROM order_t INNER JOIN product_in_order pio ON order_t.id = pio.order_id\n" +
            "        WHERE status = 'DONE') AS RowCosts\n" +
            "    GROUP BY salesman_id) AS SalesmenIncome", nativeQuery = true)
    MinMaxSalesmanIncome minMaxSalesmanIncome();



}
