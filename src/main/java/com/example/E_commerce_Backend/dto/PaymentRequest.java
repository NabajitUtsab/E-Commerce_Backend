package com.example.E_commerce_Backend.dto;

import com.example.E_commerce_Backend.enumeration.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    @NotNull(message = "Order id can not be null")
    private Long orderId;

    @NotBlank(message = "There is must be Transaction type")
    private TransactionType transactionType;
}
