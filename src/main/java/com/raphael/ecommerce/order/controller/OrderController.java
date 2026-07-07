package com.raphael.ecommerce.order.controller;

import com.raphael.ecommerce.common.response.ApiResponse;
import com.raphael.ecommerce.order.dto.response.CheckoutResponse;
import com.raphael.ecommerce.order.dto.response.OrderResponse;
import com.raphael.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CheckoutResponse> checkout() {

        return ApiResponse.success(
                HttpStatus.CREATED,
                "Order created successfully. Proceed to payment.",
                orderService.checkout()
        );

    }

    @GetMapping
    public ApiResponse<Page<OrderResponse>> getMyOrders(
            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return ApiResponse.success(
                HttpStatus.OK,
                "Orders retrieved successfully.",
                orderService.getMyOrders(pageable)
        );

    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getMyOrderById(
            @PathVariable Long orderId
    ) {

        return ApiResponse.success(
                HttpStatus.OK,
                "Order retrieved successfully.",
                orderService.getMyOrderById(orderId)
        );

    }

}