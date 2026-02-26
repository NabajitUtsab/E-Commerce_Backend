package com.example.E_commerce_Backend.service;

import com.example.E_commerce_Backend.dto.UpdateCartRequest;
import com.example.E_commerce_Backend.entity.Cart;
import com.example.E_commerce_Backend.entity.CartItem;
import com.example.E_commerce_Backend.entity.Product;
import com.example.E_commerce_Backend.entity.Users;
import com.example.E_commerce_Backend.repository.CartItemRepo;
import com.example.E_commerce_Backend.repository.CartRepo;
import com.example.E_commerce_Backend.repository.ProductRepo;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private  CartRepo cartRepo;

    @Mock
    private  ProductRepo productRepo;

    @Mock
    private  CartItemRepo cartItemRepo;

    @InjectMocks
    private CartService cartService;

    @Test
    void updateItem_success() {

        Long itemId = 1L;

        UpdateCartRequest updateCartRequest = new UpdateCartRequest();
        updateCartRequest.setQuantity(10);


        //Mock product
        Product product = new Product();
        product.setQty_left(15);
        product.setPrice(300.0);


        //Mock user
        Users users = new Users();
        users.setId(1L);

        //Mock cart
        Cart cart = new Cart();
        cart.setUser(users);

        // Mock CartItem
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);


        when(cartItemRepo.findById(itemId)).thenReturn(Optional.of(cartItem));

        Long userId = cartService.updateItem(itemId,updateCartRequest);

        assertEquals(1L,userId);
        assertEquals(10, cartItem.getQuantity());
        assertEquals(300.0, cartItem.getUnitPrice());
    }
}