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
public class AddCartItemRequest {

    @NotNull(message = "Product id needed")
    private Long product_id;

    @Min(value =1 , message = "Quantity must be greater than 1")
    private Integer quantity;
}
