package com.databases.shop.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Embedded
    private PersonName personName;

    @NotNull
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "customer")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Order> orders = new HashSet<>();

    @NotNull
    @Embedded
    private Contacts contacts;

}
