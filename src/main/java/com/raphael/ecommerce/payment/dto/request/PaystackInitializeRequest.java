package com.raphael.ecommerce.payment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaystackInitializeRequest {

    private String email;

    /**
     * Amount in kobo.
     */
    private Long amount;

    private String reference;

    @JsonProperty("callback_url")
    private String callbackUrl;

}