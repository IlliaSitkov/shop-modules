package com.databases.shop.controllers;

import com.databases.shop.exceptions.product.NoProductWithSuchArticul;
import com.databases.shop.exceptions.product.ProductIllegalArgumentException;
import com.databases.shop.models.Product;
import com.databases.shop.services.implementations.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Iterable<Product> getProducts(){
        return productService.getAll();
    }

    @GetMapping("/{articul}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Product getProduct(@PathVariable("articul") Long articul) {
        return productService.getProductByArticul(articul);
    }

    @DeleteMapping("/delete/{articul}")
    //@PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable("articul") Long articul) {
        productService.deleteProduct(articul);
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public Product addProduct(@Valid @RequestBody Product product){
        return productService.addProduct(product);
    }

    @PutMapping("/{articul}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(@PathVariable("articul") Long articul, @Valid @RequestBody Product product) {
        product.setArticul(articul);
        return productService.updateProduct(product);
    }

    @ExceptionHandler(NoProductWithSuchArticul.class)
    public ResponseEntity<Map<String,String>> handleException(NoProductWithSuchArticul ex){
        Map<String, String> result = new HashMap<>();
        result.put("found", "false");
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ProductIllegalArgumentException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleOtherExceptions(ProductIllegalArgumentException e){
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        map.put("error",e.getMessage());
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

}
