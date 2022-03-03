package com.databases.shop.services.implementations;

import com.databases.shop.models.Category;
import com.databases.shop.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Override
    public Category addCategory(String name, String description) {
        return null;
    }

    @Override
    public Category addCategory(Category category) {
        return null;
    }

    @Override
    public boolean categoryExistsById(Long id) {
        return false;
    }

    @Override
    public boolean deleteCategory(Long id) {
        return false;
    }

    @Override
    public Category updateCategory(Long id, String name, String description) {
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public Category updateCategoryNoCheck(Category category) {
        return null;
    }

    @Override
    public Category getCategoryById(Long id) {
        return null;
    }

    @Override
    public Iterable<Category> getCategoryByPartName(String name) throws Exception {
        return null;
    }

    @Override
    public Iterable<Category> getAll() {
        return null;
    }
}
