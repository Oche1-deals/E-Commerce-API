package com.raphael.ecommerce.order.dto.response;

import com.raphael.ecommerce.payment.dto.response.PaymentInitializationResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutResponse {

    private OrderResponse order;

    private PaymentInitializationResponse payment;

}