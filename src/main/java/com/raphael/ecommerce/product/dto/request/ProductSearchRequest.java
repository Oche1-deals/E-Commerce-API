package com.raphael.ecommerce.product.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductSearchRequest {

    private String name;

    private Long categoryId;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Integer page = 0;

    private Integer size = 10;

    private String sortBy = "id";

    private String direction = "asc";

}