package com.databases.shop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Provider(String name, Address address, Contacts contacts) {
        this.name = name;
        this.address = address;
        this.contacts = contacts;
    }

    public Provider(Long edrpou, String name, Address address, Contacts contacts) {
        this.edrpou = edrpou;
        this.name = name;
        this.address = address;
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provider)) return false;
        Provider provider = (Provider) o;
        return getEdrpou().equals(provider.getEdrpou()) && getName().equals(provider.getName()) &&
                getAddress().equals(provider.getAddress()) && getContacts().equals(provider.getContacts());
    }
}
