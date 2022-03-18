package com.databases.shop.controllers;

import com.databases.shop.exceptions.category.CategoryIllegalArgumentException;
import com.databases.shop.exceptions.category.NoCategoryWithSuchId;
import com.databases.shop.models.Category;
import com.databases.shop.services.implementations.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Iterable<Category> getCategories(){
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Category getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public Category addCategory(@Valid @RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Category updateCategory(@PathVariable("id") Long id, @Valid @RequestBody Category category) {
        category.setId(id);
        return categoryService.updateCategory(category);
    }

    @ExceptionHandler(NoCategoryWithSuchId.class)
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
