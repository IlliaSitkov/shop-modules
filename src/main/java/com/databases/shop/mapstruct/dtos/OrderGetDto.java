package com.databases.shop.mapstruct.dtos;

import com.databases.shop.models.Customer;
import com.databases.shop.models.OrderStatus;
import com.databases.shop.models.ProductInOrder;
import com.databases.shop.models.Salesman;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderGetDto {

    @JsonProperty("id")
    private Long id;

//    @JsonProperty("customer")
//    private CustomerGetDto customer;

    @OneToMany(mappedBy = "order")
    private Set<ProductInOrder> products = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false)
    private Date date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Min(0)
    @Column(name = "order_cost")
    private double cost;



}
