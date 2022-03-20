package com.databases.shop.repositories;

import com.databases.shop.models.Category;
import com.databases.shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE LOWER(c.name) LIKE LOWER(:name)")
    Iterable<Category> findByName(@Param("name") String name);

    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE LOWER(c.name) LIKE LOWER(:name) AND NOT c.id = :id")
    Iterable<Category> findByNameAndNotId(@Param("id") long id, @Param("name") String name);
    //@Query("select u from user u where u.username LIKE :username")
    //List<User> findUserByUsernameLike(@Param("username") String username);
}
