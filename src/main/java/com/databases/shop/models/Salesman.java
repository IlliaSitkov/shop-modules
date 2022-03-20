package com.databases.shop.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Salesman {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Embedded
    private PersonName personName;

    @NotNull
    @Embedded
    private Contacts contacts;


    @OneToMany(mappedBy = "salesman")
    private Set<Order> orders = new HashSet<>();

}
