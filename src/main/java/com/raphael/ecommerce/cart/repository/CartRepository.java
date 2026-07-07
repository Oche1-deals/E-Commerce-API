package com.raphael.ecommerce.cart.repository;

import com.raphael.ecommerce.auth.entity.User;
import com.raphael.ecommerce.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}