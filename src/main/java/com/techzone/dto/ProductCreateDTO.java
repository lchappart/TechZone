package com.techzone.dto;

import com.techzone.entity.Product;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {
    @NotBlank(message = "Le nom est requis")
    @Size(max = 200, message = "Le nom ne peut pas dépasser 200 caractères")
    private String nom;
    
    @Size(max = 2000, message = "La description ne peut pas dépasser 2000 caractères")
    private String description;
    
    @NotNull(message = "Le prix est requis")
    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0")
    private BigDecimal prix;
    
    @NotNull(message = "La catégorie est requise")
    private Long categoryId;
    
    @NotNull(message = "Le statut de stock est requis")
    private Product.StockStatus stockStatus;
    
    private Boolean promotion = false;
    
    @Size(max = 500, message = "L'URL de l'image ne peut pas dépasser 500 caractères")
    private String imageUrl;
}
