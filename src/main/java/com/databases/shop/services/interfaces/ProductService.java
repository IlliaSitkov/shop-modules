package com.databases.shop.services.interfaces;

import com.databases.shop.models.Category;
import com.databases.shop.models.Product;
import com.databases.shop.models.Provider;

public interface ProductService {

    Product addProduct(String name, String description, int quantity, double price, Provider provider, Category category);
    Product addProduct(Product product);

    boolean productExistsById(Long articul);
    //boolean productExistsByName(String name);

    boolean deleteProduct(Long articul); // throws ProductNotFoundException;
    Product updateProduct(Long articul, String name, String description);
    Product updateProduct(Product product);
    Product updateProductNoCheck(Product product);
    Product getProductById(Long articul); // throws ProductNotFoundException;

    Iterable<Product> getProductByPartName(String name) throws Exception;
    //Product getProductByName(String name) throws Exception;
    Iterable<Product> getAll();
}
