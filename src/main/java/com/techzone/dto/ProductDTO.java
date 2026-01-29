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
    private String imageUrl;
    
    /** Picsum (Lorem Picsum) : image aléatoire mais stable par produit via seed = id */
    private static final String PICSUM_BASE = "https://picsum.photos/seed/";
    private static final int PICSUM_WIDTH = 800;
    private static final int PICSUM_HEIGHT = 600;
    
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
        dto.setImageUrl(product.getImageUrl() != null && !product.getImageUrl().isBlank()
                ? product.getImageUrl()
                : getRandomPicsumUrl(product.getId()));
        return dto;
    }
    
    /** URL Picsum avec seed = id produit : chaque produit a une image "aléatoire" différente mais stable. */
    private static String getRandomPicsumUrl(Long productId) {
        long seed = productId != null ? productId : System.currentTimeMillis();
        return PICSUM_BASE + seed + "/" + PICSUM_WIDTH + "/" + PICSUM_HEIGHT;
    }
}
