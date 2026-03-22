package com.example.E_commerce_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;
    private String paymentStatus;
    private Long orderId;
    private String transactionType;
    private Double totalAmount;
    private LocalDateTime transactionDate;

}
