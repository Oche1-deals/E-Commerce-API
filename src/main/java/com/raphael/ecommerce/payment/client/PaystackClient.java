package com.raphael.ecommerce.payment.client;

import com.raphael.ecommerce.payment.config.PaystackConfig;
import com.raphael.ecommerce.payment.dto.request.PaystackInitializeRequest;
import com.raphael.ecommerce.payment.dto.response.PaystackInitializeResponse;
import com.raphael.ecommerce.payment.dto.response.PaystackVerificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class PaystackClient {

    private final RestClient restClient;
    private final PaystackConfig paystackConfig;

    public PaystackInitializeResponse initializeTransaction(
            PaystackInitializeRequest request
    ) {

        return restClient.post()

                .uri(
                        paystackConfig.getBaseUrl()
                                + "/transaction/initialize"
                )

                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + paystackConfig.getSecretKey()
                )

                .contentType(MediaType.APPLICATION_JSON)

                .body(request)

                .retrieve()

                .body(PaystackInitializeResponse.class);

    }

    public PaystackVerificationResponse verifyTransaction(
            String reference
    ) {

        return restClient.get()

                .uri(
                        paystackConfig.getBaseUrl()
                                + "/transaction/verify/" + reference
                )

                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + paystackConfig.getSecretKey()
                )

                .retrieve()

                .body(PaystackVerificationResponse.class);

    }
}