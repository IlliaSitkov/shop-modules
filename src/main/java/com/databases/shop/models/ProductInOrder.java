package com.databases.shop.models;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
public class ProductInOrder {


    @EmbeddedId
    private ProductInOrderId id;

    @MapsId(value = "productId")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToOne
    private Product product;

    @MapsId(value = "orderId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Order order;

    @Min(0)
    @Column(name = "prod_price")
    private double price;

    @Min(0)
    @Column(name = "prod_quantity")
    private double quantity;


}
