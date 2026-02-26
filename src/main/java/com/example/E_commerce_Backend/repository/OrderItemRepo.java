package com.example.E_commerce_Backend.repository;

import com.example.E_commerce_Backend.entity.Cart;
import com.example.E_commerce_Backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {


    Optional<OrderItem> findByOrderId(Long orderId);
}
