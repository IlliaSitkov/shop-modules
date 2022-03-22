package com.databases.shop.mapstruct.mappers;

import com.databases.shop.mapstruct.dtos.product.ProductGetDto;
import com.databases.shop.mapstruct.dtos.product.ProductPostDto;
import com.databases.shop.mapstruct.dtos.product.ProductPutDto;
import com.databases.shop.mapstruct.dtos.product.ProductSlimGetDto;
import com.databases.shop.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductGetDto productToProductGetDto(Product product);

    Iterable<ProductGetDto> productsToProductsGetDto(Iterable<Product> products);

    Product productPutDtoToProduct(ProductPutDto productPutDto);

    Product productPostDtoToProduct(ProductPostDto productPostDto);

    ProductSlimGetDto productToProductSlimGetDto(Product product);
    Iterable<ProductSlimGetDto> productsToProductsSlimGetDto(Iterable<Product> products);
}
