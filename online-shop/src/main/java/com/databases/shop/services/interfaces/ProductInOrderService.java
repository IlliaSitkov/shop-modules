package com.databases.shop.services.interfaces;

import com.databases.shop.mapstruct.dtos.productInOrder.ProductInOrderPostDto;
import com.databases.shop.models.ProductInOrder;

public interface ProductInOrderService {

    ProductInOrder updateProductQuantityInOrder(Long orderId, Long productId, int quantity);

    void deleteById(Long orderId, Long productId);

    ProductInOrder save(ProductInOrderPostDto productInOrderPostDto);
}
