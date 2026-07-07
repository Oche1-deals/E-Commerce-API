package com.raphael.ecommerce.product.service;

import com.raphael.ecommerce.product.dto.request.CreateProductRequest;
import com.raphael.ecommerce.product.dto.request.ProductSearchRequest;
import com.raphael.ecommerce.product.dto.request.UpdateProductRequest;
import com.raphael.ecommerce.product.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse updateProduct(
            Long id,
            UpdateProductRequest request
    );

    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProducts(int page, int size,String sortBy,String direction);

    void deleteProduct(Long id);

    Page<ProductResponse> searchProducts(
            ProductSearchRequest request
    );

}