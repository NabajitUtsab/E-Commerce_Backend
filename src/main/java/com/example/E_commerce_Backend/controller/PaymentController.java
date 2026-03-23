package com.example.E_commerce_Backend.controller;

import com.example.E_commerce_Backend.dto.PaymentRequest;
import com.example.E_commerce_Backend.dto.PaymentResponse;
import com.example.E_commerce_Backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor

public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest paymentRequest){

        PaymentResponse response = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByOrderId(@PathVariable Long orderId){

        List<PaymentResponse> paymentResponses = paymentService.getPaymentsByOrderId(orderId);

        return ResponseEntity.ok(paymentResponses);

    }


    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id){

        PaymentResponse paymentResponse = paymentService.getPaymentsById(id);

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }




}
