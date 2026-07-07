package com.raphael.ecommerce.payment.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInitializationResponse {

    private String authorizationUrl;

    private String accessCode;

    private String reference;

    private String message;

}