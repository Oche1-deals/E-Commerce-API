package com.raphael.ecommerce.product.specification;

import com.raphael.ecommerce.product.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );

    }
    public static Specification<Product> hasCategory(Long categoryId) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.equal(
                        root.get("category").get("id"),
                        categoryId
                );

    }
    public static Specification<Product> hasMinimumPrice(BigDecimal minPrice) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"),
                        minPrice
                );

    }
    public static Specification<Product> hasMaximumPrice(BigDecimal maxPrice) {

        return (root, query, criteriaBuilder) ->

                criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"),
                        maxPrice
                );

    }
}