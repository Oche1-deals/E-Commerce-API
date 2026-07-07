package com.raphael.ecommerce.payment.dto.response;

import lombok.Data;

@Data
public class PaystackVerificationResponse {

    private boolean status;

    private String message;

    private VerificationData data;

    @Data
    public static class VerificationData {

        private String reference;

        private String status;

        private Long amount;

        private String paid_at;

        private String channel;

    }

}