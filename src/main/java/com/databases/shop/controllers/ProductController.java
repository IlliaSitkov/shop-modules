package com.databases.shop.controllers;

import com.databases.shop.exceptions.product.NoProductWithSuchArticul;
import com.databases.shop.exceptions.product.ProductIllegalArgumentException;
import com.databases.shop.mapstruct.dtos.category.CategoryGetDto;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.CategoryFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.ProductFilterBoundsDto;
import com.databases.shop.mapstruct.dtos.product.ProductSlimGetDto;
import com.databases.shop.mapstruct.mappers.ProductMapper;
import com.databases.shop.mapstruct.dtos.product.ProductGetDto;
import com.databases.shop.mapstruct.dtos.product.ProductPostDto;
import com.databases.shop.mapstruct.dtos.product.ProductPutDto;
import com.databases.shop.mapstruct.mappers.ProductMapper;
import com.databases.shop.models.Product;
import com.databases.shop.services.implementations.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@Validated
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Iterable<ProductGetDto> getProducts(){
        return productMapper.productsToProductsGetDto(productService.getAll());
    }

    @GetMapping("/slim")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Iterable<ProductSlimGetDto> getSlimProducts(){
        return productMapper.productsToProductsSlimGetDto(productService.getAll());
    }

    @GetMapping("/{articul}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public ProductGetDto getProduct(@PathVariable("articul") Long articul) {
        return productMapper.productToProductGetDto(productService.getProductByArticul(articul));
    }

    @DeleteMapping("/{articul}")
    //@PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable("articul") Long articul) {
        productService.deleteProduct(articul);
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ProductGetDto addProduct(@Valid @RequestBody ProductPostDto productPostDto){
        return productMapper.productToProductGetDto(productService.addProduct(productMapper.productPostDtoToProduct(productPostDto)));
    }

    @PutMapping("/{articul}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ProductGetDto updateProduct(@PathVariable("articul") Long articul, @Valid @RequestBody ProductPutDto productPutDto) {
        Product product = productMapper.productPutDtoToProduct(productPutDto);
        product.setArticul(articul);
        return productMapper.productToProductGetDto(productService.updateProduct(product));
    }

    @GetMapping("/filter/{quantity}/{price}/{providersString}/{categoriesString}")
    public Iterable<ProductGetDto> getFilteredProducts(@PathVariable("quantity") int quantity, @PathVariable("price") double price, @PathVariable("providersString") String providersString, @PathVariable("categoriesString") String categoriesString){
        List<Long> providersEdrpous = Arrays.stream(providersString.split(",")).map(Long::parseLong).collect(Collectors.toList());
        List<Long> categoriesIds = Arrays.stream(categoriesString.split(",")).map(Long::parseLong).collect(Collectors.toList());
        return productMapper.productsToProductsGetDto(productService.getFilteredProducts(quantity, price, providersEdrpous, categoriesIds));
    }

    @GetMapping("/filterBounds")
    public ProductFilterBoundsDto getFilterBounds() {
        return productService.getProductFilterBounds();
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
