package com.raphael.ecommerce.auth.mapper;

import com.raphael.ecommerce.auth.dto.request.RegisterRequest;
import com.raphael.ecommerce.auth.dto.response.UserResponse;
import com.raphael.ecommerce.auth.entity.User;
import com.raphael.ecommerce.auth.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}