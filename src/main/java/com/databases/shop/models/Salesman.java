package com.databases.shop.models;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Salesman {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Embedded
    private PersonName personName;

    @OneToMany(mappedBy = "salesman")
    private Set<Order> orders = new HashSet<>();

}
