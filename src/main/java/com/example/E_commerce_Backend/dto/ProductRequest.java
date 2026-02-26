package com.example.E_commerce_Backend.dto;

import com.example.E_commerce_Backend.enumeration.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductRequest {

    @NotNull
    private String name;
    private String description;
    private String sku;

    @Min(value = 1,message = "price can not be less than 1")
    private Double price;
    private Category category;

    @Min(value = 0,message = "Negative value is not allowed")
    private Integer qty_left;
    private String product_photo;
}
