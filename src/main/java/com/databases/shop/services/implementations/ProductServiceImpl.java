package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.category.UnableToDeleteCategoryException;
import com.databases.shop.exceptions.product.NoProductWithSuchArticul;
import com.databases.shop.exceptions.product.ProductIllegalArgumentException;
import com.databases.shop.exceptions.product.UnableToDeleteProductException;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.ProductFilterBoundsDto;
import com.databases.shop.models.Category;
import com.databases.shop.models.Product;
import com.databases.shop.models.Provider;
import com.databases.shop.repositories.ProductRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.repositories.queryinterfaces.ProductReportValues;
import com.databases.shop.services.interfaces.ProductService;
import com.databases.shop.utils.Utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

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
        try {
            productRepository.deletePByArticul(articul);
        }
        catch (Exception e) {
            throw new UnableToDeleteProductException();
        }
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
            //return productRepository.save(product);
            productRepository.update(articul, finalName, description, quantity, price, provider.getEdrpou(), category.getId());
            return product;
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
        return productRepository.getPByArticul(articul).orElseThrow(() -> new NoProductWithSuchArticul(articul));
    }

    @Override
    public Iterable<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    public Iterable<Product> getFilteredProducts(int quantity, double price, List<Long> providersEdrpous, List<Long> categoriesIds) {
        return productRepository.findFilteredProducts(quantity, price, providersEdrpous, categoriesIds);
    }

    @Override
    public Iterable<Product> getFilteredProductsWithProduct(int quantity, double price, List<Long> providersEdrpous, List<Long> categoriesIds, Long productArticul) {
        return productRepository.findFilteredProductsWithProduct(quantity, price, providersEdrpous, categoriesIds, productArticul);
    }

    @Override
    public ProductFilterBoundsDto getProductFilterBounds() {

        MinMaxValues minMaxQuantity = productRepository.minMaxProductsQuantity();
        MinMaxValues minMaxPrice = productRepository.minMaxProductsPrice();

        ProductFilterBoundsDto productFilterBoundsDto = new ProductFilterBoundsDto();

        productFilterBoundsDto.setMinQuantity((int)minMaxQuantity.getMinValue());
        productFilterBoundsDto.setMaxQuantity((int)minMaxQuantity.getMaxValue());
        productFilterBoundsDto.setMinPrice(minMaxPrice.getMinValue());
        productFilterBoundsDto.setMaxPrice(minMaxPrice.getMaxValue());

        return productFilterBoundsDto;
    }

}
