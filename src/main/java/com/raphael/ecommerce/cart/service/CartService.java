package com.raphael.ecommerce.cart.service;

import com.raphael.ecommerce.cart.dto.request.AddCartItemRequest;
import com.raphael.ecommerce.cart.dto.request.UpdateCartItemRequest;
import com.raphael.ecommerce.cart.dto.response.CartResponse;

public interface CartService {

    CartResponse addItem(AddCartItemRequest request);

    CartResponse getCart();

    CartResponse updateItem(
            Long cartItemId,
            UpdateCartItemRequest request
    );

    void removeItem(Long cartItemId);

    void clearCart();

}