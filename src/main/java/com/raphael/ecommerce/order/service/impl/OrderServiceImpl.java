package com.raphael.ecommerce.order.service.impl;

import com.raphael.ecommerce.auth.repository.UserRepository;
import com.raphael.ecommerce.auth.service.CurrentUserService;
import com.raphael.ecommerce.cart.entity.Cart;
import com.raphael.ecommerce.cart.entity.CartItem;
import com.raphael.ecommerce.cart.repository.CartItemRepository;
import com.raphael.ecommerce.cart.repository.CartRepository;
import com.raphael.ecommerce.exception.BusinessException;
import com.raphael.ecommerce.order.dto.response.CheckoutResponse;
import com.raphael.ecommerce.order.dto.response.OrderResponse;
import com.raphael.ecommerce.order.entity.Order;
import com.raphael.ecommerce.order.entity.OrderItem;
import com.raphael.ecommerce.order.entity.OrderStatus;
import com.raphael.ecommerce.order.mapper.OrderMapper;
import com.raphael.ecommerce.order.repository.OrderRepository;
import com.raphael.ecommerce.order.service.OrderService;
import com.raphael.ecommerce.payment.dto.response.PaymentInitializationResponse;
import com.raphael.ecommerce.payment.gateway.PaymentGateway;
import com.raphael.ecommerce.product.entity.Product;
import com.raphael.ecommerce.product.repository.ProductRepository;
import com.raphael.ecommerce.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.raphael.ecommerce.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;
    private final CurrentUserService currentUserService;
    private final PaymentGateway paymentGateway;


    @Override
    @Transactional
    public CheckoutResponse checkout() {

        User user = currentUserService.getCurrentUser();

        // Get user's cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found.")
                );

        // Get all cart items
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new BusinessException("Your cart is empty.");
        }

        // Create order
        Order order = Order.builder()
                .orderNumber("ORD-" + System.currentTimeMillis())
                .user(user)
                .status(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        // Keep track of updated products
        List<Product> updatedProducts = new ArrayList<>();

        // Convert cart items to order items
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            // Product must still be active
            if (!product.getActive()) {
                throw new BusinessException(
                        product.getName() + " is no longer available."
                );
            }

            // Check stock
            if (cartItem.getQuantity() > product.getStockQuantity()) {
                throw new BusinessException(
                        "Insufficient stock for " + product.getName()
                );
            }

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .subtotal(subtotal)
                    .build();

            order.getOrderItems().add(orderItem);

            totalAmount = totalAmount.add(subtotal);

            // Reduce stock
            product.setStockQuantity(
                    product.getStockQuantity() - cartItem.getQuantity()
            );

            updatedProducts.add(product);
        }

        // Save all updated products in one batch
        productRepository.saveAll(updatedProducts);

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // Clear cart
        cartItemRepository.deleteByCart(cart);

        // Initialize payment
        PaymentInitializationResponse paymentResponse =
                paymentGateway.initializePayment(savedOrder);

        return CheckoutResponse.builder()
                .order(orderMapper.toResponse(savedOrder))
                .payment(paymentResponse)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> getMyOrders(
            Pageable pageable
    ) {

        User user = currentUserService.getCurrentUser();

        return orderRepository.findByUser(user, pageable)
                .map(orderMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getMyOrderById(
            Long orderId
    ) {

        User user = currentUserService.getCurrentUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found.")
                );

        if (!order.getUser().getId().equals(user.getId())) {
            throw new BusinessException(
                    "You are not authorized to view this order."
            );
        }

        return orderMapper.toResponse(order);

    }
}