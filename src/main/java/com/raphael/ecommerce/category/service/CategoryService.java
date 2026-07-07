package com.raphael.ecommerce.category.service;

import com.raphael.ecommerce.category.dto.request.CreateCategoryRequest;
import com.raphael.ecommerce.category.dto.request.UpdateCategoryRequest;
import com.raphael.ecommerce.category.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(
            Long id,
            UpdateCategoryRequest request
    );

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    void deleteCategory(Long id);

}