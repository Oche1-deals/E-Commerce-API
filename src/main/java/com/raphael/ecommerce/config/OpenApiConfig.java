package com.raphael.ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ecommerceApi() {

        final String securitySchemeName = "Bearer Authentication";

        return new OpenAPI()

                .info(
                        new Info()
                                .title("E-Commerce API")
                                .version("1.0.0")
                                .description("""
                                        A RESTful E-Commerce API built with Spring Boot.
                                        
                                        Features:
                                        • JWT Authentication & Authorization
                                        • Product & Category Management
                                        • Shopping Cart
                                        • Order Management
                                        • Paystack Payment Integration
                                        • Inventory Management
                                        """)
                                .contact(
                                        new Contact()
                                                .name("Raphael Odoh")
                                                .email("odohraphaeloche11@gmail.com")
                                )
                                .license(
                                        new License()
                                                .name("MIT License")
                                )
                )

                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                );
    }

}