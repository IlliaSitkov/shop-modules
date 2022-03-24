package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.product.NoProductWithSuchArticul;
import com.databases.shop.exceptions.product.ProductIllegalArgumentException;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.CategoryFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.ProductFilterBoundsDto;
import com.databases.shop.models.Category;
import com.databases.shop.models.Product;
import com.databases.shop.models.Provider;
import com.databases.shop.repositories.ProductRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxCustomersQuantity;
import com.databases.shop.repositories.queryinterfaces.MinMaxPrice;
import com.databases.shop.repositories.queryinterfaces.MinMaxProductsQuantity;
import com.databases.shop.services.interfaces.ProductService;
import com.databases.shop.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Utils utils;

    @Override
    public Product addProduct(String name, String description, int quantity, double price, Provider provider, Category category) {
        name = utils.processString(name);
        utils.checkName(name);
        utils.checkQuantOfProduct(quantity);
        utils.checkPriceOfProduct(price);
        Iterable<Product> productsWithSuchName = productRepository.findByName(name);
        if (productsWithSuchName.iterator().hasNext())
            throw new ProductIllegalArgumentException("Product with such name already exists!");
        //TODO: Do we need to write save, update, delete, getAll methods by ourselves?
        return productRepository.save(new Product(name, description, quantity, price, provider, category));
    }

    @Override
    public Product addProduct(Product product) {
        product.setArticul(-1L);
        return addProduct(product.getName(), product.getDescription(), product.getQuantity(), product.getPrice(), product.getProvider(), product.getCategory());
    }

    @Override
    public boolean productExistsByArticul(Long articul) {
        return productRepository.existsById(articul);
    }

    @Override
    public boolean deleteProduct(Long articul) {
        if(!productExistsByArticul(articul)) throw new NoProductWithSuchArticul(articul);
        productRepository.deleteById(articul);
        return true;
    }

    @Override
    public Product updateProduct(Long articul, String name, String description, int quantity,
                                 double price, Provider provider, Category category) {
        name = utils.processString(name);
        utils.checkName(name);
        utils.checkQuantOfProduct(quantity);
        utils.checkPriceOfProduct(price);
        Iterable<Product> productsWithSuchName = productRepository.findByNameAndNotArticul(articul, name);
        if (productsWithSuchName.iterator().hasNext())
            throw new ProductIllegalArgumentException("Product with such name already exists!");
        String finalName = name;
        return productRepository.findById(articul).map((product) -> {
            if (nothingChanged(product, finalName, description,
                    quantity, price, provider, category))
                return product;

            product.setName(finalName);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setPrice(price);
            product.setProvider(provider);
            product.setCategory(category);
            return productRepository.save(product);
        }).orElseGet(() -> {
            return productRepository.save(new Product(articul, finalName, description,
                    quantity, price, provider, category));
        });
    }

    private boolean nothingChanged(Product product, String name, String description, int quantity,
                                   double price, Provider provider, Category category) {
        return product.getName().equals(name) && product.getDescription().equals(description)
                && product.getQuantity() == quantity && product.getPrice() == price
                && product.getProvider().equals(provider) && product.getCategory().equals(category);
    }


    @Override
    public Product updateProduct(Product product) {
        return updateProduct(product.getArticul(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice(), product.getProvider(), product.getCategory());
    }

    @Override
    public Product updateProductNoCheck(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductByArticul(Long articul) {
        return productRepository.findById(articul).orElseThrow(() -> new NoProductWithSuchArticul(articul));
    }

    @Override
    public Iterable<Product> getAll() {
        List<Product> products = productRepository.findAll();
        //Collections.sort(products);
        return products;
    }

    @Override
    public Iterable<Product> getFilteredProducts(int quantity, double price, List<Long> providersEdrpous, List<Long> categoriesIds) {
        return productRepository.findFilteredProducts(quantity, price, providersEdrpous, categoriesIds);
    }

    @Override
    public ProductFilterBoundsDto getProductFilterBounds() {

        MinMaxProductsQuantity minMaxQuantity = productRepository.minMaxProductsQuantity();
        MinMaxPrice minMaxPrice = productRepository.minMaxProductsPrice();

        ProductFilterBoundsDto productFilterBoundsDto = new ProductFilterBoundsDto();

        productFilterBoundsDto.setMinQuantity(minMaxQuantity.getMinQuantity());
        productFilterBoundsDto.setMaxQuantity(minMaxQuantity.getMaxQuantity());
        productFilterBoundsDto.setMinPrice(minMaxPrice.getMinPrice());
        productFilterBoundsDto.setMaxPrice(minMaxPrice.getMaxPrice());

        return productFilterBoundsDto;
    }
}
