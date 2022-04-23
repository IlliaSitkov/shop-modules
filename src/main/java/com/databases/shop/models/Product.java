package com.databases.shop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

// product(articul, name, description, quantity, price, edrpou, cat_number).
@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "product")
    //@OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<ProductInOrder> productsInOrder = new HashSet<>();

    public Product(String name, String description, int quantity, double price, Provider provider, Category category) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.provider = provider;
        this.category = category;
    }

    public Product(Long articul, String name, String description, int quantity, double price, Provider provider, Category category) {
        this.articul = articul;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.provider = provider;
        this.category = category;
    }
}
