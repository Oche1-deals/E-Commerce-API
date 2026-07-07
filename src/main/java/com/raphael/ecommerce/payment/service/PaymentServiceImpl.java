package com.raphael.ecommerce.payment.service;

import com.raphael.ecommerce.exception.BusinessException;
import com.raphael.ecommerce.exception.ResourceNotFoundException;
import com.raphael.ecommerce.order.entity.Order;
import com.raphael.ecommerce.order.entity.OrderStatus;
import com.raphael.ecommerce.order.repository.OrderRepository;
import com.raphael.ecommerce.payment.gateway.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentGateway paymentGateway;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void verifyPayment(String reference) {

        Order order = orderRepository.findByOrderNumber(reference)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found.")
                );

        boolean successful =
                paymentGateway.verifyPayment(reference);

        if (!successful) {
            throw new BusinessException("Payment verification failed.");
        }

        order.setStatus(OrderStatus.PROCESSING);

        orderRepository.save(order);
    }

}