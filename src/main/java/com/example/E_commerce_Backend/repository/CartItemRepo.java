package com.example.E_commerce_Backend.repository;

import com.example.E_commerce_Backend.entity.Cart;
import com.example.E_commerce_Backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {


    List<CartItem> findCartItemsByCartId(Long cartId);

    Optional<CartItem> findCartItemsByCartIdAndProduct_Id(Long cartId, Long productId);

    void deleteCartItemByCartId(Long cartId);
}
