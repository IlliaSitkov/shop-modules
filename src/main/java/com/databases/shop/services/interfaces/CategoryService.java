package com.databases.shop.services.interfaces;

import com.databases.shop.models.Category;

import java.util.Set;

public interface CategoryService {

    Category addCategory(String name, String description);
    Category addCategory(Category category);

    boolean categoryExistsById(Long id);
    //boolean categoryExistsByName(String name);

    boolean deleteCategory(Long id); // throws CategoryNotFoundException;
    Category updateCategory(Long id, String name, String description);
    Category updateCategory(Category category);
    Category updateCategoryNoCheck(Category category);
    Category getCategoryById(Long id); // throws CategoryNotFoundException;

    Iterable<Category> getCategoryByPartName(String name) throws Exception;
    //Category getCategoryByName(String name) throws Exception;
    Iterable<Category> getAll();
}
