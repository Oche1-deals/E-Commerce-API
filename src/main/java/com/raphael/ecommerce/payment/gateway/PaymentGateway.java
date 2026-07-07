package com.raphael.ecommerce.payment.gateway;

import com.raphael.ecommerce.order.entity.Order;
import com.raphael.ecommerce.payment.dto.response.PaymentInitializationResponse;

public interface PaymentGateway {

    PaymentInitializationResponse initializePayment(
            Order order
    );

    boolean verifyPayment(
            String reference
    );

}