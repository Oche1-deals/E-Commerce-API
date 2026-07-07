package com.raphael.ecommerce.product.service.impl;

import com.raphael.ecommerce.exception.ResourceNotFoundException;
import com.raphael.ecommerce.product.dto.request.CreateProductRequest;
import com.raphael.ecommerce.product.dto.request.ProductSearchRequest;
import com.raphael.ecommerce.product.dto.request.UpdateProductRequest;
import com.raphael.ecommerce.product.dto.response.ProductResponse;
import com.raphael.ecommerce.product.entity.Product;
import com.raphael.ecommerce.product.mapper.ProductMapper;
import com.raphael.ecommerce.product.repository.ProductRepository;
import com.raphael.ecommerce.product.service.ProductService;
import com.raphael.ecommerce.product.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.raphael.ecommerce.category.entity.Category;
import com.raphael.ecommerce.category.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        Product product = productMapper.toEntity(request);

        Category category = getCategory(request.getCategoryId());

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);

    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse updateProduct(
            Long id,
            UpdateProductRequest request
    ) {

        Product product = getProduct(id);

        productMapper.updateEntity(request, product);

        Category category = getCategory(request.getCategoryId());

        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);

    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductById(Long id) {

        Product product = getProduct(id);

        return productMapper.toResponse(product);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponse> getAllProducts(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products =
                productRepository.findAll(pageable);

        return products.map(productMapper::toResponse);

    }

    @Transactional(readOnly = true)
    @Override
    public void deleteProduct(Long id) {

        Product product = getProduct(id);

        product.setActive(false);

        productRepository.save(product);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponse> searchProducts(
            ProductSearchRequest request
    ) {

        Sort sort = request.getDirection().equalsIgnoreCase("desc")
                ? Sort.by(request.getSortBy()).descending()
                : Sort.by(request.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                sort
        );

        Specification<Product> specification = Specification.unrestricted();

        if (request.getName() != null && !request.getName().isBlank()) {

            specification = specification.and(
                    ProductSpecification.hasName(request.getName())
            );

        }

        if (request.getCategoryId() != null) {

            specification = specification.and(
                    ProductSpecification.hasCategory(
                            request.getCategoryId()
                    )
            );

        }

        if (request.getMinPrice() != null) {

            specification = specification.and(
                    ProductSpecification.hasMinimumPrice(
                            request.getMinPrice()
                    )
            );

        }

        if (request.getMaxPrice() != null) {

            specification = specification.and(
                    ProductSpecification.hasMaximumPrice(
                            request.getMaxPrice()
                    )
            );

        }

        Page<Product> products =
                productRepository.findAll(
                        specification,
                        pageable
                );

        return products.map(productMapper::toResponse);

    }

    /**
     * Helper method used internally to retrieve a product
     * or throw an exception if it does not exist.
     */
    private Product getProduct(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        )
                );

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