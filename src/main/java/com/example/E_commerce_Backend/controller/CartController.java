package com.example.E_commerce_Backend.controller;


import com.example.E_commerce_Backend.dto.AddCartItemRequest;
import com.example.E_commerce_Backend.dto.CartResponse;
import com.example.E_commerce_Backend.dto.UpdateCartRequest;
import com.example.E_commerce_Backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;


    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam Long userId) {
        CartResponse cartResponse = cartService.getTheCart(userId);
        return ResponseEntity.ok(cartResponse);
    }


    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddCartItemRequest addCartItemRequest,@RequestParam Long userId){
         cartService.createItem(addCartItemRequest,userId);
        CartResponse cartResponse = cartService.getTheCart(userId);
        return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
    }


    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateToCart(@PathVariable Long itemId, @Valid @RequestBody UpdateCartRequest updateCartRequest){

        Long userId = cartService.updateItem(itemId,updateCartRequest);
        CartResponse cartResponse = cartService.getTheCart(userId);

        return new ResponseEntity<>(cartResponse,HttpStatus.OK);

    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> deleteFromCart(@PathVariable Long itemId){
        Long userId = cartService.deleteItem(itemId);

        CartResponse cartResponse = cartService.getTheCart(userId);

        return new ResponseEntity<>(cartResponse,HttpStatus.OK);
    }

}
