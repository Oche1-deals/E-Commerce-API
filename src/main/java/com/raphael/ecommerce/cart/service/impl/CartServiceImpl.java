package com.raphael.ecommerce.cart.service.impl;

import com.raphael.ecommerce.auth.entity.User;
import com.raphael.ecommerce.auth.repository.UserRepository;
import com.raphael.ecommerce.auth.service.CurrentUserService;
import com.raphael.ecommerce.cart.dto.request.AddCartItemRequest;
import com.raphael.ecommerce.cart.dto.request.UpdateCartItemRequest;
import com.raphael.ecommerce.cart.dto.response.CartResponse;
import com.raphael.ecommerce.cart.entity.Cart;
import com.raphael.ecommerce.cart.entity.CartItem;
import com.raphael.ecommerce.cart.mapper.CartMapper;
import com.raphael.ecommerce.cart.repository.CartItemRepository;
import com.raphael.ecommerce.cart.repository.CartRepository;
import com.raphael.ecommerce.cart.service.CartService;
import com.raphael.ecommerce.exception.BusinessException;
import com.raphael.ecommerce.exception.ResourceNotFoundException;
import com.raphael.ecommerce.product.entity.Product;
import com.raphael.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional
    public CartResponse addItem(AddCartItemRequest request) {

        User user = currentUserService.getCurrentUser();

        // Get or create cart
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() ->
                        cartRepository.save(
                                Cart.builder()
                                        .user(user)
                                        .build()
                        )
                );

        // Find product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found.")
                );

        // Product must be active
        if (!product.getActive()) {
            throw new BusinessException("Product is not available.");
        }

        // Check if product already exists in cart
        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem == null) {

            if (request.getQuantity() > product.getStockQuantity()) {
                throw new BusinessException("Insufficient stock available.");
            }

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();

        } else {

            int newQuantity = cartItem.getQuantity() + request.getQuantity();

            if (newQuantity > product.getStockQuantity()) {
                throw new BusinessException("Insufficient stock available.");
            }

            cartItem.setQuantity(newQuantity);
        }

        cartItemRepository.save(cartItem);

        return cartMapper.toCartResponse(
                cart,
                cartItemRepository.findByCart(cart)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart() {

        User user = currentUserService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() ->
                        Cart.builder()
                                .user(user)
                                .build()
                );

        List<CartItem> cartItems = cart.getId() == null
                ? Collections.emptyList()
                : cartItemRepository.findByCart(cart);

        return cartMapper.toCartResponse(
                cart,
                cartItems
        );
    }

    @Override
    @Transactional
    public CartResponse updateItem(
            Long cartItemId,
            UpdateCartItemRequest request
    ) {

        User user = currentUserService.getCurrentUser();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found.")
                );

        // Verify ownership
        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new BusinessException(
                    "You cannot modify another user's cart."
            );
        }

        Product product = cartItem.getProduct();

        if (!product.getActive()) {
            throw new BusinessException(
                    "Product is no longer available."
            );
        }

        if (request.getQuantity() > product.getStockQuantity()) {
            throw new BusinessException(
                    "Insufficient stock available."
            );
        }

        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        Cart cart = cartItem.getCart();

        return cartMapper.toCartResponse(
                cart,
                cartItemRepository.findByCart(cart)
        );
    }

    @Override
    @Transactional
    public void removeItem(Long cartItemId) {

        User user = currentUserService.getCurrentUser();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found.")
                );

        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new BusinessException(
                    "You cannot modify another user's cart."
            );
        }

        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public void clearCart() {

        User user = currentUserService.getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found.")
                );

        cartItemRepository.deleteByCart(cart);
    }
}