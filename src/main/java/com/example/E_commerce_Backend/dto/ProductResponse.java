package com.example.E_commerce_Backend.dto;

import com.example.E_commerce_Backend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    List<Product> products;
}
