package com.example.E_commerce_Backend.dto;

import com.example.E_commerce_Backend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSuccessResponse {

    private String productName;
    private Long productId;
    private String message;


}
