package com.example.E_commerce_Backend.repository;

import com.example.E_commerce_Backend.entity.Cart;
import com.example.E_commerce_Backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
}
