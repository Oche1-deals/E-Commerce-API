package com.raphael.ecommerce.payment.service;

import com.raphael.ecommerce.order.entity.Order;
import com.raphael.ecommerce.payment.client.PaystackClient;
import com.raphael.ecommerce.payment.dto.request.PaystackInitializeRequest;
import com.raphael.ecommerce.payment.dto.response.PaymentInitializationResponse;
import com.raphael.ecommerce.payment.dto.response.PaystackInitializeResponse;
import com.raphael.ecommerce.payment.dto.response.PaystackVerificationResponse;
import com.raphael.ecommerce.payment.gateway.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaystackPaymentService implements PaymentGateway {

    private final PaystackClient paystackClient;

    @Override
    public PaymentInitializationResponse initializePayment(
            Order order
    ) {

        Long amountInKobo = order.getTotalAmount()
                .multiply(BigDecimal.valueOf(100))
                .longValue();

        PaystackInitializeRequest request =
                PaystackInitializeRequest.builder()
                        .email(order.getUser().getEmail())
                        .amount(amountInKobo)
                        .reference(order.getOrderNumber())
                        .callbackUrl(
                                "http://localhost:5173/payment/callback"
                        )
                        .build();

        PaystackInitializeResponse response =
                paystackClient.initializeTransaction(request);

        return PaymentInitializationResponse.builder()
                .authorizationUrl(
                        response.getData().getAuthorization_url()
                )
                .accessCode(
                        response.getData().getAccess_code()
                )
                .reference(
                        response.getData().getReference()
                )
                .message(
                        response.getMessage()
                )
                .build();

    }

    @Override
    public boolean verifyPayment(String reference) {

        PaystackVerificationResponse response =
                paystackClient.verifyTransaction(reference);

        System.out.println("API Status: " + response.isStatus());
        System.out.println("Message: " + response.getMessage());

        if (response.getData() != null) {
            System.out.println("Transaction Status: " + response.getData().getStatus());
            System.out.println("Reference: " + response.getData().getReference());
        }

        return response.isStatus()
                && response.getData() != null
                && "success".equalsIgnoreCase(
                response.getData().getStatus()
        );
    }

}