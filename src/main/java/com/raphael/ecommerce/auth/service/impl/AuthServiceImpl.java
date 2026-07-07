package com.raphael.ecommerce.auth.service.impl;

import com.raphael.ecommerce.auth.dto.request.LoginRequest;
import com.raphael.ecommerce.auth.dto.request.RegisterRequest;
import com.raphael.ecommerce.auth.dto.response.LoginResponse;
import com.raphael.ecommerce.auth.dto.response.UserResponse;
import com.raphael.ecommerce.auth.entity.User;
import com.raphael.ecommerce.auth.mapper.UserMapper;
import com.raphael.ecommerce.auth.repository.UserRepository;
import com.raphael.ecommerce.auth.service.AuthService;
import com.raphael.ecommerce.exception.DuplicateResourceException;
import com.raphael.ecommerce.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists."
            );
        }

        User user = userMapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found."
                        )
                );

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .user(userMapper.toResponse(user))
                .build();

    }

}