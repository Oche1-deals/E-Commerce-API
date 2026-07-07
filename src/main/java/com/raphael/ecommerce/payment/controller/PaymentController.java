package com.raphael.ecommerce.payment.controller;

import com.raphael.ecommerce.common.response.ApiResponse;
import com.raphael.ecommerce.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/verify/{reference}")
    public ApiResponse<Void> verifyPayment(
            @PathVariable String reference
    ) {

        paymentService.verifyPayment(reference);

        return ApiResponse.success(
                HttpStatus.OK,
                "Payment verified successfully.",
                null
        );

    }

}