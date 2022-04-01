package com.databases.shop.repositories;

import com.databases.shop.models.Product;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.repositories.queryinterfaces.ProductReportValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p " +
           "FROM Product p " +
           "WHERE LOWER(p.name) LIKE LOWER( :name)")
    Iterable<Product> findByName(@Param("name") String name);

    @Query("SELECT p " +
           "FROM Product p " +
           "WHERE LOWER(p.name) LIKE LOWER( :name) AND NOT p.articul = :articul")
    Iterable<Product> findByNameAndNotArticul(@Param("articul") long articul, @Param("name") String name);

    @Query(value =
            "SELECT *\n" +
            "FROM product\n" +
            "WHERE :quantity <= quantity\n" +
            "AND :price <= price\n" +
            "AND provider_fk IN :providersEdrpous\n" +
            "AND category_fk IN :categoriesIds", nativeQuery = true)
    Iterable<Product> findFilteredProducts(int quantity, double price, List<Long> providersEdrpous, List<Long> categoriesIds);

    @Query(value =
            "SELECT *\n" +
            "FROM product\n" +
            "WHERE :quantity <= quantity\n" +
            "AND :price <= price\n" +
            "AND provider_fk IN :providersEdrpous\n" +
            "AND category_fk IN :categoriesIds\n" +
            "AND NOT EXISTS(\n" +
                    "SELECT *\n" +
                    "FROM ((product p INNER JOIN product_in_order ON p.articul = product_articul)\n" +
                    "   INNER JOIN order_t ON product_in_order.order_id = order_t.id) PRO\n" +
                    "WHERE PRO.product_articul = :productArticul AND PRO.status = 'DONE' AND NOT EXISTS(\n" +
                    "   SELECT *\n" +
                    "   FROM order_t O\n" +
                    "   WHERE id = PRO.order_id AND EXISTS(\n" +
                    "       SELECT *\n" +
                    "       FROM (product_in_order INNER JOIN order_t OT ON product_in_order.order_id = OT.id) PO\n" +
                    "       WHERE PO.status = 'DONE' AND PO.order_id = O.id AND PO.product_articul = product.articul)))\n" +
            "AND articul NOT IN(\n" +
                "SELECT PRO1.product_articul\n" +
                "FROM ((product p0 INNER JOIN product_in_order ON p0.articul = product_articul)\n" +
                "   INNER JOIN order_t ON product_in_order.order_id = order_t.id) PRO1\n" +
                "WHERE PRO1.status = 'DONE' AND PRO1.order_id NOT IN (\n" +
                    "SELECT PRO2.order_id\n" +
                    "FROM ((product p1 INNER JOIN product_in_order ON p1.articul = product_articul)\n" +
                    "   INNER JOIN order_t ON product_in_order.order_id = order_t.id) PRO2\n" +
                    "WHERE PRO2.status = 'DONE' AND PRO2.product_articul = :productArticul\n))", nativeQuery = true)
    Iterable<Product> findFilteredProductsWithProduct(int quantity, double price, List<Long> providersEdrpous, List<Long> categoriesIds, Long productArticul);

    @Query(value =
            "SELECT COALESCE (MIN(quantity),0) AS minValue, COALESCE (MAX(quantity),0) AS maxValue\n" +
                    "FROM\n" +
                    "   (SELECT articul, quantity\n" +
                    "    FROM product\n" +
                    "    GROUP BY articul) AS ProductsQuantities", nativeQuery = true)
    MinMaxValues minMaxProductsQuantity();

    @Query(value =
            "SELECT COALESCE (MIN(price),0) AS minValue, COALESCE (MAX(price),0) AS maxValue\n" +
                    "FROM\n" +
                    "   (SELECT articul, price\n" +
                    "    FROM product\n" +
                    "    GROUP BY articul) AS ProductsPrices", nativeQuery = true)
    MinMaxValues minMaxProductsPrice();

    @Query(value = "SELECT articul, p.name, COALESCE(SUM(prod_quantity),0) AS soldQuantity, COALESCE(SUM(prod_quantity*prod_price),0) AS soldCost, COALESCE(AVG(prod_quantity),0) AS averageQuantityInOrder, COALESCE(COUNT(DISTINCT customer_id),0) AS quantityOfCustomers\n" +
                   "FROM (product p JOIN product_in_order ON articul = product_articul)\n" +
                   "      JOIN order_t ON id = order_id\n" +
                   "WHERE status = 'DONE' AND date_created >= :dateStart AND date_created <= :dateEnd\n" +
                   "GROUP BY articul", nativeQuery = true)
    Iterable<ProductReportValues> productReport(LocalDate dateStart, LocalDate dateEnd);
}
