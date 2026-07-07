package com.raphael.ecommerce.order.mapper;

import com.raphael.ecommerce.order.dto.response.OrderItemResponse;
import com.raphael.ecommerce.order.dto.response.OrderResponse;
import com.raphael.ecommerce.order.entity.Order;
import com.raphael.ecommerce.order.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(this::toOrderItemResponse)
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();

    }

    private OrderItemResponse toOrderItemResponse(
            OrderItem item
    ) {

        return OrderItemResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subtotal(item.getSubtotal())
                .build();

    }

}