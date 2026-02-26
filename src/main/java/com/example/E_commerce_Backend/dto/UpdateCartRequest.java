package com.example.E_commerce_Backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCartRequest {

    @NotNull(message = "Quantity is required")
    @Min(value =1 , message = "Quantity must be greater than 1")
    private Integer quantity;
}
