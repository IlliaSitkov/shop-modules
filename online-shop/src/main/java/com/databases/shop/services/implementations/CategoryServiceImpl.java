package com.databases.shop.services.implementations;

import com.databases.shop.exceptions.category.CategoryIllegalArgumentException;
import com.databases.shop.exceptions.category.NoCategoryWithSuchId;
import com.databases.shop.exceptions.category.UnableToDeleteCategoryException;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.CategoryFilterBoundsDto;
import com.databases.shop.models.Category;
import com.databases.shop.repositories.CategoryRepository;
import com.databases.shop.repositories.queryinterfaces.MinMaxValues;
import com.databases.shop.services.interfaces.CategoryService;
import com.databases.shop.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private Utils utils;

    @Override
    public Category addCategory(String name, String description) {
        name = utils.processString(name);
        utils.checkName(name);
        Iterable<Category> categoryWithSuchName = categoryRepository.findByName(name);
        if (categoryWithSuchName.iterator().hasNext())
            throw new CategoryIllegalArgumentException("Category with such name already exists!");
        return categoryRepository.save(new Category(name, description));
    }

    @Override
    public Category addCategory(Category category) {
        category.setId(-1L);
        return addCategory(category.getName(), category.getDescription());
    }

    @Override
    public boolean categoryExistsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public boolean deleteCategory(Long id) {
        if(!categoryExistsById(id)) throw new NoCategoryWithSuchId(id);
        try {
            categoryRepository.deleteCById(id);
        }
        catch (Exception e) {
            throw new UnableToDeleteCategoryException();
        }
        return true;
    }

    @Override
    public Category updateCategory(Long id, String name, String description) {
        name = utils.processString(name);
        utils.checkName(name);

        Iterable<Category> categoriesWithSuchName = categoryRepository.findByNameAndNotId(id, name);
        if (categoriesWithSuchName.iterator().hasNext())
            throw new CategoryIllegalArgumentException("Category with such name already exists!");
        String finalName = name;
        return categoryRepository.findById(id).map((category) -> {
            if (nothingChanged(category, finalName, description))
                return category;

            category.setName(finalName);
            category.setDescription(description);

            // return categoryRepository.save(category);
            categoryRepository.update(id, finalName, description);
            return category;
        }).orElseGet(() -> {
            return categoryRepository.save(new Category(id, finalName, description));
        });
    }

    private boolean nothingChanged(Category category, String name, String description) {
        return category.getName().equals(name) && category.getDescription().equals(description);
    }

    @Override
    public Category updateCategory(Category category) {
        return updateCategory(category.getId(), category.getName(), category.getDescription());
    }

    @Override
    public Category updateCategoryNoCheck(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.getCById(id).orElseThrow(() -> new NoCategoryWithSuchId(id));
    }

    @Override
    public Iterable<Category> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public Iterable<Category> getCategoriesFilteredByCustomersAndProductsQuantity(int customersQuant, int productsQuant) {
        return categoryRepository.findHavingQuantityOfCustomersBiggerAndQuantityOfProductsSoldBigger(customersQuant, productsQuant);
    }

    @Override
    public Iterable<Category> getCategoriesFilteredByCustomersAndProductsQuantityWithMaxProductsQuantity(int customersQuant, int productsQuant) {
        return categoryRepository.findHavingQuantityOfCustomersBiggerAndQuantityOfProductsSoldBiggerWithMaxProductsQuantity(customersQuant, productsQuant);
    }

    @Override
    public CategoryFilterBoundsDto getCategoryFilterBounds() {

        MinMaxValues minMaxCustomersQuantity = categoryRepository.minMaxCustomersQuantity();
        MinMaxValues minMaxProductsQuantity = categoryRepository.minMaxProductsQuantity();

        CategoryFilterBoundsDto categoryFilterBoundsDto = new CategoryFilterBoundsDto();

        categoryFilterBoundsDto.setMinCustomersQuant((int)minMaxCustomersQuantity.getMinValue());
        categoryFilterBoundsDto.setMaxCustomersQuant((int)minMaxCustomersQuantity.getMaxValue());
        categoryFilterBoundsDto.setMinProductsQuant((int)minMaxProductsQuantity.getMinValue());
        categoryFilterBoundsDto.setMaxProductsQuant((int)minMaxProductsQuantity.getMaxValue());

        return categoryFilterBoundsDto;
    }
}
