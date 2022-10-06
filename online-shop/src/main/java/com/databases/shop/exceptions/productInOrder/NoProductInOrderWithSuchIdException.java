package com.databases.shop.exceptions.productInOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NoProductInOrderWithSuchIdException  extends RuntimeException {

    public NoProductInOrderWithSuchIdException(Long orderId, Long productId) {
        super("No order with such id: ("+orderId+", "+productId);
    }

}