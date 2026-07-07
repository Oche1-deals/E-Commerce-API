package com.raphael.ecommerce.category.mapper;

import com.raphael.ecommerce.category.dto.request.CreateCategoryRequest;
import com.raphael.ecommerce.category.dto.request.UpdateCategoryRequest;
import com.raphael.ecommerce.category.dto.response.CategoryResponse;
import com.raphael.ecommerce.category.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CreateCategoryRequest request) {

        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

    }

    public CategoryResponse toResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .active(category.getActive())
                .build();

    }

    public void updateEntity(
            UpdateCategoryRequest request,
            Category category
    ) {

        category.setName(request.getName());
        category.setDescription(request.getDescription());

    }

}