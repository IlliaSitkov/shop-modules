package com.databases.shop.mapstruct.mappers;

import com.databases.shop.mapstruct.dtos.product.ProductSlimGetDto;
import com.databases.shop.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductSlimGetDto productToProductSlimGetDto(Product product);
    Iterable<ProductSlimGetDto> productsToProductsSlimGetDto(Iterable<Product> products);

}
