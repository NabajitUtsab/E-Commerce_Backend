package com.example.E_commerce_Backend.entity;

import com.example.E_commerce_Backend.enumeration.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Entity
@Data
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String sku;
    private Double price;
    private Category category;
    private Integer qty_left;
    private String product_photo;

    @ManyToOne
    @JoinColumn(name = "created_By")
    private Users createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_By")
    private Users updatedBy;


}
