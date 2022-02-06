package com.databases.shop.models;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Table
public class Provider {

    @Id
    @Column
    private Long edrpou;

    @NotNull
    @Column(nullable = false)
    private String name;

    //Column?
    @NotNull
    @Embedded
    private Address address;

    //Column?
    @NotNull
    @Embedded
    private Contacts contacts;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Product> products;
}
