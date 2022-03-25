package com.databases.shop.controllers;

import com.databases.shop.mapstruct.dtos.productInOrder.ProductInOrderGetDto;
import com.databases.shop.mapstruct.mappers.ProductInOrderMapper;
import com.databases.shop.models.ProductInOrder;
import com.databases.shop.services.interfaces.ProductInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/order_lines")
public class ProductInOrderController {


    @Autowired
    private ProductInOrderService productInOrderService;

    @Autowired
    private ProductInOrderMapper mapper;


    @PutMapping("/productQuantity")
    public ProductInOrderGetDto updateProductQuantityInOrder(@RequestParam Long orderId, @RequestParam Long productId, @RequestParam int quantity) {
        return mapper.productInOrderToProductInOrderGetDto(productInOrderService.updateProductQuantityInOrder(orderId,productId,quantity));
    }

    @DeleteMapping("/{orderId}/{productId}")
    public void deleteProductInOrderById(@PathVariable Long orderId, @PathVariable Long productId) {
        productInOrderService.deleteById(orderId,productId);
    }








}
