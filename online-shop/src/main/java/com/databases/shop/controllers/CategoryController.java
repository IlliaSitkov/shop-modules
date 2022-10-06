package com.databases.shop.controllers;

import com.databases.shop.exceptions.category.CategoryIllegalArgumentException;
import com.databases.shop.exceptions.category.NoCategoryWithSuchId;
import com.databases.shop.mapstruct.dtos.category.CategoryGetDto;
import com.databases.shop.mapstruct.dtos.category.CategoryPostDto;
import com.databases.shop.mapstruct.dtos.category.CategoryPutDto;
import com.databases.shop.mapstruct.dtos.category.CategorySlimGetDto;
import com.databases.shop.mapstruct.dtos.filterBoundsDtos.CategoryFilterBoundsDto;
import com.databases.shop.mapstruct.mappers.CategoryMapper;
import com.databases.shop.models.Category;
import com.databases.shop.services.implementations.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@Validated
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Iterable<CategoryGetDto> getCategories(){
        return categoryMapper.categoriesToCategoriesGetDto(categoryService.getAll());
    }

    @GetMapping("slim")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Iterable<CategorySlimGetDto> getSlimCategories(){
        return categoryMapper.categoriesToCategoriesSlimGetDto(categoryService.getAll());
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public CategoryGetDto getCategory(@PathVariable("id") Long id) {
        return categoryMapper.categoryToCategoryGetDto(categoryService.getCategoryById(id));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public CategoryGetDto addCategory(@Valid @RequestBody CategoryPostDto categoryPostDto){
        return categoryMapper.categoryToCategoryGetDto(categoryService.addCategory(categoryMapper.categoryPostDtoToCategory(categoryPostDto)));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public CategoryGetDto updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryPutDto categoryPutDto) {
        Category category = categoryMapper.categoryPutDtoToCategory(categoryPutDto);
        category.setId(id);
        return categoryMapper.categoryToCategoryGetDto(categoryService.updateCategory(category));
    }

    @GetMapping("/filterCustomersAndProductsQuantity/{customersQuant}/{productsQuant}")
    public Iterable<CategoryGetDto> getCategoriesFilteredByCustomersAndProductsQuantity(@PathVariable("customersQuant") int customersQuant, @PathVariable("productsQuant") int productsQuant){
        return categoryMapper.categoriesToCategoriesGetDto(categoryService.getCategoriesFilteredByCustomersAndProductsQuantity(customersQuant, productsQuant));
    }

    @GetMapping("/filterCustomersAndProductsQuantityWithMaxProductsQuantity/{customersQuant}/{productsQuant}")
    public Iterable<CategoryGetDto> getCategoriesFilteredByCustomersAndProductsQuantityWithMaxProductsQuantity(@PathVariable("customersQuant") int customersQuant, @PathVariable("productsQuant") int productsQuant){
        return categoryMapper.categoriesToCategoriesGetDto(categoryService.getCategoriesFilteredByCustomersAndProductsQuantityWithMaxProductsQuantity(customersQuant, productsQuant));
    }

    @GetMapping("/filterBounds")
    public CategoryFilterBoundsDto getFilterBounds() {
        return categoryService.getCategoryFilterBounds();
    }

    @ExceptionHandler(NoCategoryWithSuchId.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String,String>> handleException(NoCategoryWithSuchId ex){
        Map<String, String> result = new HashMap<>();
        result.put("found", "false");
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CategoryIllegalArgumentException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleOtherExceptions(CategoryIllegalArgumentException e){
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        map.put("error",e.getMessage());
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

}
