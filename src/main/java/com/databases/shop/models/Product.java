package com.databases.shop.models;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// product(articul, name, description, quantity, price, edrpou, cat_number).
@Entity
@Getter
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long articul;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private int quantity;

    @NotNull
    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "provider_fk", nullable = false)
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

}
