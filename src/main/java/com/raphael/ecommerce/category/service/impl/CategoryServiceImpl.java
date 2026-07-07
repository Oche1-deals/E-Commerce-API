package com.raphael.ecommerce.category.service.impl;

import com.raphael.ecommerce.category.dto.request.CreateCategoryRequest;
import com.raphael.ecommerce.category.dto.request.UpdateCategoryRequest;
import com.raphael.ecommerce.category.dto.response.CategoryResponse;
import com.raphael.ecommerce.category.entity.Category;
import com.raphael.ecommerce.category.mapper.CategoryMapper;
import com.raphael.ecommerce.category.repository.CategoryRepository;
import com.raphael.ecommerce.category.service.CategoryService;
import com.raphael.ecommerce.exception.DuplicateResourceException;
import com.raphael.ecommerce.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {

        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException(
                    "Category already exists."
            );
        }

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);

    }

    @Override
    public CategoryResponse updateCategory(
            Long id,
            UpdateCategoryRequest request
    ) {

        Category category = getCategory(id);

        if (!category.getName().equalsIgnoreCase(request.getName())
                && categoryRepository.existsByNameIgnoreCase(request.getName())) {

            throw new DuplicateResourceException(
                    "Category already exists."
            );
        }

        categoryMapper.updateEntity(request, category);

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(updatedCategory);

    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        return categoryMapper.toResponse(
                getCategory(id)
        );

    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();

    }

    @Override
    public void deleteCategory(Long id) {

        Category category = getCategory(id);

        // Soft delete
        category.setActive(false);

        categoryRepository.save(category);

    }

    private Category getCategory(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id
                        )
                );

    }

}