package com.example.E_commerce_Backend.repository;

import com.example.E_commerce_Backend.entity.Cart;
import com.example.E_commerce_Backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByUserIdOrderByOrderTimeDesc(Long userId);
}
