package com.raphael.ecommerce.order.service;

import com.raphael.ecommerce.order.dto.response.CheckoutResponse;
import com.raphael.ecommerce.order.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    CheckoutResponse checkout();

    Page<OrderResponse> getMyOrders(
            Pageable pageable
    );

    OrderResponse getMyOrderById(
            Long orderId
    );

}