package com.raphael.ecommerce.product.controller;

import com.raphael.ecommerce.common.response.ApiResponse;
import com.raphael.ecommerce.product.dto.request.CreateProductRequest;
import com.raphael.ecommerce.product.dto.request.ProductSearchRequest;
import com.raphael.ecommerce.product.dto.request.UpdateProductRequest;
import com.raphael.ecommerce.product.dto.response.ProductResponse;
import com.raphael.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody CreateProductRequest request
    ) {

        ProductResponse response = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                HttpStatus.CREATED,
                                "Product created successfully.",
                                response
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProducts(

            @RequestParam(required = false)
            String name,

            @RequestParam(required = false)
            Long categoryId,

            @RequestParam(required = false)
            BigDecimal minPrice,

            @RequestParam(required = false)
            BigDecimal maxPrice,

            @RequestParam(defaultValue = "0")
            Integer page,

            @RequestParam(defaultValue = "10")
            Integer size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction

    ) {

        ProductSearchRequest request = new ProductSearchRequest();

        request.setName(name);
        request.setCategoryId(categoryId);
        request.setMinPrice(minPrice);
        request.setMaxPrice(maxPrice);
        request.setPage(page);
        request.setSize(size);
        request.setSortBy(sortBy);
        request.setDirection(direction);

        Page<ProductResponse> response =
                productService.searchProducts(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Products retrieved successfully.",
                        response
                )
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @PathVariable Long id
    ) {

        ProductResponse response =
                productService.getProductById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Product retrieved successfully.",
                        response
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request
    ) {

        ProductResponse response =
                productService.updateProduct(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Product updated successfully.",
                        response
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable Long id
    ) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Product deleted successfully.",
                        null
                )
        );
    }
}