package com.example.E_commerce_Backend.service;

import com.example.E_commerce_Backend.dto.ProductRequest;
import com.example.E_commerce_Backend.dto.ProductSuccessResponse;
import com.example.E_commerce_Backend.entity.Product;
import com.example.E_commerce_Backend.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepo productRepo;

    public List<Product> getAllProduct() {

        return productRepo.findAll();
    }


    public ResponseEntity<ProductSuccessResponse> getProduct(long id) {

        Product product = productRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));

        ProductSuccessResponse productSuccessResponse = ProductSuccessResponse.builder().
                productId(product.getId()).
                productName(product.getName()).
                message("Your Search completed successfully!").
                build();

        return ResponseEntity.ok(productSuccessResponse);

    }


    public ResponseEntity<ProductSuccessResponse> createProduct(ProductRequest productRequest) {
        Product newProduct = Product.builder().
                name(productRequest.getName()).
                description(productRequest.getDescription()).
                sku(productRequest.getSku()).
                price(productRequest.getPrice()).
                category(productRequest.getCategory()).
                qty_left(productRequest.getQty_left()).
                product_photo(productRequest.getProduct_photo()).
                build();

        ProductSuccessResponse productSuccessResponse = ProductSuccessResponse.builder().
                productId(newProduct.getId()).
                productName(newProduct.getName()).
                message("Your Product added successfully!").
                build();

        return new ResponseEntity<>(productSuccessResponse,HttpStatus.CREATED);
    }


    public ResponseEntity<ProductSuccessResponse> updateProduct(long id, ProductRequest productRequest) {

        Product existingProduct = productRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));

        existingProduct.setName(productRequest.getName());

        if(productRequest.getDescription()!=null) {
            existingProduct.setDescription(productRequest.getDescription());
        }
        if(productRequest.getSku()!=null) {
            existingProduct.setSku(productRequest.getSku());
        }
        if(productRequest.getPrice()!=null) {
            existingProduct.setPrice(productRequest.getPrice());
        }
        if(productRequest.getCategory()!=null) {
            existingProduct.setCategory(productRequest.getCategory());
        }
        if(productRequest.getQty_left()!=null) {
            existingProduct.setQty_left(productRequest.getQty_left());
        }
        if(productRequest.getProduct_photo()!=null) {
            existingProduct.setProduct_photo(productRequest.getProduct_photo());
        }


        ProductSuccessResponse productSuccessResponse = ProductSuccessResponse.builder().
                productId(existingProduct.getId()).
                productName(existingProduct.getName()).
                message("Your Product updated successfully!").
                build();

        return new ResponseEntity<>(productSuccessResponse,HttpStatus.OK);
    }


    public ResponseEntity<ProductSuccessResponse> deleteProduct(long id) {

        Product product = productRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));

        productRepo.delete(product);

        ProductSuccessResponse productSuccessResponse = ProductSuccessResponse.builder().
                productId(product.getId()).
                productName(product.getName()).
                message("Your Product deleted successfully!").
                build();

        return new ResponseEntity<>(productSuccessResponse,HttpStatus.OK);

    }
}
