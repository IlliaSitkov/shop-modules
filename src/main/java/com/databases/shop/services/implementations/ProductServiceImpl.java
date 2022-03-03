package com.databases.shop.services.implementations;

import com.databases.shop.models.Category;
import com.databases.shop.models.Product;
import com.databases.shop.models.Provider;
import com.databases.shop.services.interfaces.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product addProduct(String name, String description, int quantity, double price, Provider provider, Category category) {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public boolean productExistsById(Long articul) {
        return false;
    }

    @Override
    public boolean deleteProduct(Long articul) {
        return false;
    }

    @Override
    public Product updateProduct(Long articul, String name, String description) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProductNoCheck(Product product) {
        return null;
    }

    @Override
    public Product getProductById(Long articul) {
        return null;
    }

    @Override
    public Iterable<Product> getProductByPartName(String name) throws Exception {
        return null;
    }

    @Override
    public Iterable<Product> getAll() {
        return null;
    }
}
