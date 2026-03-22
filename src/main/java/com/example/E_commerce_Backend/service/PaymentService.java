package com.example.E_commerce_Backend.service;

import com.example.E_commerce_Backend.dto.PaymentRequest;
import com.example.E_commerce_Backend.dto.PaymentResponse;
import com.example.E_commerce_Backend.entity.Order;
import com.example.E_commerce_Backend.entity.Payment;
import com.example.E_commerce_Backend.enumeration.OrderStatus;
import com.example.E_commerce_Backend.enumeration.PaymentStatus;
import com.example.E_commerce_Backend.enumeration.TransactionType;
import com.example.E_commerce_Backend.exception.InvalidPaymentException;
import com.example.E_commerce_Backend.repository.OrderRepo;
import com.example.E_commerce_Backend.repository.PaymentRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;
    private final Random random = new Random();


    @Transactional
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {

        Order order = orderRepo.findById(paymentRequest.getOrderId()).
                orElseThrow(() -> new NoSuchElementException("No order found with id" + paymentRequest.getOrderId()));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new InvalidPaymentException("Payment can only be made for orders in PENDING status. Current status:" + order.getOrderStatus());
        }

        List<Payment> completedPayments = paymentRepo.findByOrderIdAndStatus(order.getId(), PaymentStatus.COMPLETED);

        if (!completedPayments.isEmpty()) {
            throw new InvalidPaymentException("Order already has a complete payment ");
        }


        Payment payment = new Payment();
                payment.setOrder(order);
                payment.setTransactionType(paymentRequest.getTransactionType());
                payment.setTotalAmount(order.getTotal_amount());
                payment.setTransactionTime(LocalDateTime.now());


        Boolean paymentSuccessful = simulatePaymentProcess(paymentRequest.getTransactionType(), order.getTotal_amount());

        if (paymentSuccessful) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            order.set_paid(true);
            order.setOrderStatus(OrderStatus.CONFIRMED);
            orderRepo.save(order);
            log.info("Payment successfully completed");
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            log.warn("Payment failed {}", order.getId());
        }


        Payment savePayment = paymentRepo.save(payment);

        return PaymentResponse.builder()
                .paymentStatus(savePayment.getPaymentStatus().name())
                .orderId(savePayment.getOrder().getId())
                .id(savePayment.getId())
                .transactionType(savePayment.getTransactionType().name())
                .totalAmount(savePayment.getTotalAmount())
                .transactionDate(savePayment.getTransactionTime())
                .build();


    }


    private Boolean simulatePaymentProcess(TransactionType transactionType, Double totalAmount) {

        Double successRate = 0d;

        switch (transactionType) {

            case CREDIT_CARD -> successRate = 0.90;
            case DEBIT_CARD -> successRate = 0.85;
            case BANK_TRANSFER -> successRate = 0.80;
            case MOBILE_PAYMENT -> successRate = 0.75;
        }

        if (totalAmount > 100000) {
            log.warn("Payment exceeds {} 100000", totalAmount);
            return false;
        }

        return random.nextDouble() < successRate;
    }


}
