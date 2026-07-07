package com.raphael.ecommerce.cart.mapper;

import com.raphael.ecommerce.cart.dto.response.CartItemResponse;
import com.raphael.ecommerce.cart.dto.response.CartResponse;
import com.raphael.ecommerce.cart.entity.Cart;
import com.raphael.ecommerce.cart.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    public CartItemResponse toCartItemResponse(
            CartItem cartItem
    ) {

        BigDecimal subtotal = cartItem.getProduct()
                .getPrice()
                .multiply(
                        BigDecimal.valueOf(cartItem.getQuantity())
                );

        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .imageUrl(cartItem.getProduct().getImageUrl())
                .price(cartItem.getProduct().getPrice())
                .quantity(cartItem.getQuantity())
                .subtotal(subtotal)
                .build();

    }

    public CartResponse toCartResponse(
            Cart cart,
            List<CartItem> cartItems
    ) {

        List<CartItemResponse> itemResponses = cartItems.stream()
                .map(this::toCartItemResponse)
                .toList();

        BigDecimal totalAmount = cartItems.stream()

                .map(item ->
                        item.getProduct()
                                .getPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                item.getQuantity()
                                        )
                                )
                )

                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        Integer totalItems = cartItems.stream()

                .mapToInt(CartItem::getQuantity)

                .sum();

        return CartResponse.builder()
                .id(cart.getId())
                .items(itemResponses)
                .totalItems(totalItems)
                .totalAmount(totalAmount)
                .build();

    }

}