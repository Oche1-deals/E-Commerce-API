package com.raphael.ecommerce.auth.controller;

import com.raphael.ecommerce.auth.dto.request.LoginRequest;
import com.raphael.ecommerce.auth.dto.request.RegisterRequest;
import com.raphael.ecommerce.auth.dto.response.LoginResponse;
import com.raphael.ecommerce.auth.dto.response.UserResponse;
import com.raphael.ecommerce.auth.service.AuthService;
import com.raphael.ecommerce.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                HttpStatus.CREATED,
                                "User registered successfully.",
                                response
                        )
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Login successful.",
                        response
                )
        );
    }

}