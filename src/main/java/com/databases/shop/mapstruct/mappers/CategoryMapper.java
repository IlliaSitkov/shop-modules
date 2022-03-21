package com.databases.shop.mapstruct.mappers;

import com.databases.shop.mapstruct.dtos.category.CategoryGetDto;
import com.databases.shop.mapstruct.dtos.category.CategoryPostDto;
import com.databases.shop.mapstruct.dtos.category.CategoryPutDto;
import com.databases.shop.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryGetDto categoryToCategoryGetDto(Category category);

    Iterable<CategoryGetDto> categoriesToCategoriesGetDto(Iterable<Category> categories);

    Category categoryPutDtoToCategory(CategoryPutDto categoryPutDto);

    Category categoryPostDtoToCategory(CategoryPostDto categoryPostDto);
}
