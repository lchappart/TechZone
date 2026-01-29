package com.techzone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String nom;
    
    @Column(length = 2000)
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus;
    
    @Column(nullable = false)
    private Boolean promotion = false;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines = new ArrayList<>();
    
    public enum StockStatus {
        EN_STOCK, RUPTURE
    }
}
