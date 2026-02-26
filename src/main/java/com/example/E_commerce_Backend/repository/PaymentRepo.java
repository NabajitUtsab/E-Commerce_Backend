package com.example.E_commerce_Backend.repository;

import com.example.E_commerce_Backend.entity.Cart;
import com.example.E_commerce_Backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderId(Long orderId);

    List<Payment> findByOrderIdAndStatus(Long orderId, String status);
}
