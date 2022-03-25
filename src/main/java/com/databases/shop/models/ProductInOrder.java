package com.databases.shop.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
public class ProductInOrder {


    @EmbeddedId
    private ProductInOrderId id;

    @MapsId(value = "productArticul")
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
    private int quantity;


}
