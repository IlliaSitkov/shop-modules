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
public class Category {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Product> products;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return getId().equals(category.getId()) && getName().equals(category.getName()) &&
                getDescription().equals(category.getDescription());
    }

}
