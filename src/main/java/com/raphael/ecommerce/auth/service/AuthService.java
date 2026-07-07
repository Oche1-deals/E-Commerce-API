package com.raphael.ecommerce.auth.service;

import com.raphael.ecommerce.auth.dto.request.LoginRequest;
import com.raphael.ecommerce.auth.dto.request.RegisterRequest;
import com.raphael.ecommerce.auth.dto.response.LoginResponse;
import com.raphael.ecommerce.auth.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}