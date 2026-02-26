package com.example.E_commerce_Backend.controller;

import com.example.E_commerce_Backend.dto.ProductRequest;
import com.example.E_commerce_Backend.dto.ProductResponse;
import com.example.E_commerce_Backend.dto.ProductSuccessResponse;
import com.example.E_commerce_Backend.entity.Product;
import com.example.E_commerce_Backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductResponse> getALlProducts(){
        List<Product> products = productService.getAllProduct();

        ProductResponse productResponse = new ProductResponse(products);

        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSuccessResponse> getProductById(@PathVariable long id){

        return productService.getProduct(id);

    }

    @PostMapping
    public ResponseEntity<ProductSuccessResponse> addProduct(@Valid @RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductSuccessResponse> updateProductById(@PathVariable long id, @Valid @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id,productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductSuccessResponse> deleteProductById(@PathVariable long id){
        return productService.deleteProduct(id);
    }


}
