package com.raphael.ecommerce.cart.controller;

import com.raphael.ecommerce.cart.dto.request.AddCartItemRequest;
import com.raphael.ecommerce.cart.dto.request.UpdateCartItemRequest;
import com.raphael.ecommerce.cart.dto.response.CartResponse;
import com.raphael.ecommerce.cart.service.CartService;
import com.raphael.ecommerce.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CartResponse> addItem(
            @Valid @RequestBody AddCartItemRequest request
    ) {

        return ApiResponse.success(
                HttpStatus.CREATED,
                "Item added to cart successfully.",
                cartService.addItem(request)
        );

    }

    @GetMapping
    public ApiResponse<CartResponse> getCart() {

        return ApiResponse.success(
                HttpStatus.OK,
                "Cart retrieved successfully.",
                cartService.getCart()
        );

    }

    @PutMapping("/items/{cartItemId}")
    public ApiResponse<CartResponse> updateItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {

        return ApiResponse.success(
                HttpStatus.OK,
                "Cart item updated successfully.",
                cartService.updateItem(cartItemId, request)
        );

    }

    @DeleteMapping("/items/{cartItemId}")
    public ApiResponse<Void> removeItem(
            @PathVariable Long cartItemId
    ) {

        cartService.removeItem(cartItemId);

        return ApiResponse.success(
                HttpStatus.OK,
                "Cart item removed successfully.",
                null
        );

    }

    @DeleteMapping
    public ApiResponse<Void> clearCart() {

        cartService.clearCart();

        return ApiResponse.success(
                HttpStatus.OK,
                "Cart cleared successfully.",
                null
        );

    }

}