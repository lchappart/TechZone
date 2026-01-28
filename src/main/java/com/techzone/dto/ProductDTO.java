package com.techzone.dto;

import com.techzone.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private Long categoryId;
    private String categoryName;
    private Product.StockStatus stockStatus;
    private Boolean promotion;
    
    public static ProductDTO fromEntity(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setNom(product.getNom());
        dto.setDescription(product.getDescription());
        dto.setPrix(product.getPrix());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getNom());
        dto.setStockStatus(product.getStockStatus());
        dto.setPromotion(product.getPromotion());
        return dto;
    }
}
