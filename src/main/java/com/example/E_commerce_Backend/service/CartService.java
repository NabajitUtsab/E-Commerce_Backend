package com.example.E_commerce_Backend.service;


import com.example.E_commerce_Backend.dto.AddCartItemRequest;
import com.example.E_commerce_Backend.dto.CartItemResponse;
import com.example.E_commerce_Backend.dto.CartResponse;
import com.example.E_commerce_Backend.dto.UpdateCartRequest;
import com.example.E_commerce_Backend.entity.Cart;
import com.example.E_commerce_Backend.entity.CartItem;
import com.example.E_commerce_Backend.entity.Product;
import com.example.E_commerce_Backend.repository.CartItemRepo;
import com.example.E_commerce_Backend.repository.CartRepo;
import com.example.E_commerce_Backend.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;


    public CartResponse getTheCart(Long userId) {

        Cart cart = getTheOrderOrCreateCart(userId);
        List<CartItem> cartItemList = cartItemRepo.findCartItemsByCartId(cart.getId());

        List<CartItemResponse> cartItemResponses = cartItemList.stream().map(items->new CartItemResponse(
                items.getId(),
                items.getProduct().getId(),
                items.getProduct().getName(),
                items.getUnitPrice(),
                items.getQuantity()
        )).toList();

        return new CartResponse(cart.getId(),cart.getTotal_amount(),cartItemResponses);

    }



    public void createItem(AddCartItemRequest addCartItemRequest, Long userId) {

        Cart cart = getTheOrderOrCreateCart(userId);

        Product product = productRepo.findById(addCartItemRequest.getProduct_id()).
                orElseThrow(()-> new NoSuchElementException("Product not found"));

        Integer quantity = addCartItemRequest.getQuantity();

        if(product.getQty_left() < quantity){
            throw new RuntimeException("Your input quantity exceeds the Leftover quantity");
        }

        Optional<CartItem> existingCartItem = cartItemRepo.findCartItemsByCartIdAndProduct_Id(cart.getId(), product.getId());

        if(existingCartItem.isPresent()){
            CartItem cartItem = existingCartItem.get();

            int  new_quantity = cartItem.getQuantity() + addCartItemRequest.getQuantity();

            if (new_quantity > product.getQty_left()){
                throw new RuntimeException("Your quantity exceeds the Leftover quantity");

            }

            cartItem.setQuantity(new_quantity);
            cartItem.setUnitPrice(product.getPrice());


            cartItemRepo.save(cartItem);

        }else
        {
            CartItem newCartItem = CartItem.builder().
                    product(product).
                    cart(cart).
                    quantity(quantity).
                    unitPrice(product.getPrice()).
                    build();

            cartItemRepo.save(newCartItem);
        }

        calculateTotalPrice(cart);

    }



    public Long updateItem(Long itemId, UpdateCartRequest updateCartRequest) {

        CartItem cartItem = cartItemRepo.findById(itemId).
                orElseThrow(()->new NoSuchElementException("There is no such CartItem"));

        Product product = cartItem.getProduct();

        if(updateCartRequest.getQuantity() > product.getQty_left()){
            throw new RuntimeException("Your input quantity exceeds the Leftover quantity");
        }

        cartItem.setQuantity(updateCartRequest.getQuantity());
        cartItem.setUnitPrice(product.getPrice());

        cartItemRepo.save(cartItem);

        calculateTotalPrice(cartItem.getCart());

        return cartItem.getCart().getUser().getId();
    }


    public Long deleteItem(Long itemId) {
        CartItem cartItem = cartItemRepo.findById(itemId).orElseThrow(()-> new NoSuchElementException("There is no such CartItem"));

        Cart cart = cartItem.getCart();

        cartItemRepo.delete(cartItem);

        calculateTotalPrice(cart);

        return cart.getUser().getId();

    }

    private Cart getTheOrderOrCreateCart(Long userId) {

        Cart cart = cartRepo.findByUserId(userId).orElseGet(()->{
                    Cart newCart = new Cart();
                    newCart.setTotal_amount(0.0);
                    newCart.setCartItemList(new ArrayList<>());

                    return cartRepo.save(newCart);
                }
        );

        return cart;
    }

    private void calculateTotalPrice(Cart cart) {

        List<CartItem> cartItemList = cartItemRepo.findCartItemsByCartId(cart.getId());

        Double totalPrice = 0d;
        for (CartItem item : cartItemList) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();

        }
        cart.setTotal_amount(totalPrice);
        cartRepo.save(cart);
    }


}
