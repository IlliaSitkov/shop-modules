package com.databases.shop.repositories;

import com.databases.model.ProductReportValues;
import com.databases.shop.models.Product;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value =
            "SELECT *\n" +
            "FROM product\n" +
            "ORDER BY name", nativeQuery = true)
    Iterable<Product> getAll();

    @Query(value =
            "SELECT *\n" +
            "FROM product\n" +
            "WHERE articul = :articul", nativeQuery = true)
    Optional<Product> getPByArticul(long articul);

    @Query(value =
            "SELECT *\n" +
            "FROM product\n" +
            "WHERE LOWER(name) LIKE LOWER(:name)", nativeQuery = true)
    Iterable<Product> findByName(String name);

    @Query(value =
           "SELECT *\n" +
           "FROM product\n" +
           "WHERE LOWER(name) LIKE LOWER(:name) AND NOT articul = :articul", nativeQuery = true)
    Iterable<Product> findByNameAndNotArticul(long articul, String name);

    @Transactional
    @Modifying
    @Query(value =
            "DELETE FROM product\n" +
            "WHERE articul = :articul", nativeQuery = true)
    void deletePByArticul(long articul);

    @Transactional
    @Modifying
    @Query(value =
            "UPDATE product\n" +
            "SET name = :name,\n" +
            "description = :description,\n" +
            "quantity = :quantity,\n" +
            "price = :price,\n" +
            "provider_fk = :providerEdrpou,\n" +
            "category_fk = :categoryId\n" +
            "WHERE articul = :articul", nativeQuery = true)
    void update(Long articul, String name, String description, int quantity, double price, long providerEdrpou, long categoryId);

    @Query(value =
            "SELECT *\n" +
            "FROM product\n" +
            "WHERE :quantity <= quantity\n" +
            "AND :price <= price\n" +
            "AND provider_fk IN :providersEdrpous\n" +
            "AND category_fk IN :categoriesIds\n" +
            "ORDER BY name", nativeQuery = true)
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
                    "WHERE PRO2.status = 'DONE' AND PRO2.product_articul = :productArticul\n))\n" +
            "ORDER BY name", nativeQuery = true)
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
                   "FROM product p LEFT OUTER JOIN\n" +
                         "(product_in_order JOIN order_t ON id = order_id) ON articul = product_articul\n" +
                   "WHERE (status = 'DONE' OR status IS NULL)\n" +
                   "AND (date_created BETWEEN :dateStart AND :dateEnd) OR date_created IS NULL\n" +
                   "GROUP BY articul\n" +
                   "ORDER BY articul", nativeQuery = true)
    Iterable<ProductReportValues> productReport(LocalDate dateStart, LocalDate dateEnd);

    @Query(value = "SELECT COALESCE(SUM(prod_quantity),0) AS soldQuantity\n" +
                   "FROM product p JOIN\n" +
                        "(product_in_order JOIN order_t ON id = order_id) ON articul = product_articul\n" +
                   "WHERE status = 'DONE' AND (date_created BETWEEN :dateStart AND :dateEnd)\n", nativeQuery = true)
    int getSoldProductsQuant(LocalDate dateStart, LocalDate dateEnd);
}
