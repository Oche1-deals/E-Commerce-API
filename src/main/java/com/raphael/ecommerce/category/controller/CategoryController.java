package com.raphael.ecommerce.category.controller;

import com.raphael.ecommerce.category.dto.request.CreateCategoryRequest;
import com.raphael.ecommerce.category.dto.request.UpdateCategoryRequest;
import com.raphael.ecommerce.category.dto.response.CategoryResponse;
import com.raphael.ecommerce.category.service.CategoryService;
import com.raphael.ecommerce.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CreateCategoryRequest request
    ) {

        CategoryResponse response =
                categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                HttpStatus.CREATED,
                                "Category created successfully.",
                                response
                        )
                );

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {

        List<CategoryResponse> response =
                categoryService.getAllCategories();

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Categories retrieved successfully.",
                        response
                )
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(
            @PathVariable Long id
    ) {

        CategoryResponse response =
                categoryService.getCategoryById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Category retrieved successfully.",
                        response
                )
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequest request
    ) {

        CategoryResponse response =
                categoryService.updateCategory(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Category updated successfully.",
                        response
                )
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable Long id
    ) {

        categoryService.deleteCategory(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Category deleted successfully.",
                        null
                )
        );

    }

}