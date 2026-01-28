package com.techzone.service;

import com.techzone.dto.ProductCreateDTO;
import com.techzone.dto.ProductDTO;
import com.techzone.entity.Category;
import com.techzone.entity.Product;
import com.techzone.repository.CategoryRepository;
import com.techzone.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public Page<ProductDTO> getAllProducts(int page, int size, Long categoryId, Boolean promotion, Product.StockStatus stockStatus) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;
        
        if (categoryId != null && promotion != null && promotion && stockStatus != null) {
            products = productRepository.findByCategoryIdAndPromotionAndStockStatus(categoryId, stockStatus, pageable);
        } else if (categoryId != null && promotion != null && promotion) {
            products = productRepository.findByCategoryIdAndPromotion(categoryId, pageable);
        } else if (categoryId != null && stockStatus != null) {
            products = productRepository.findByCategoryIdAndStockStatus(categoryId, stockStatus, pageable);
        } else if (promotion != null && promotion && stockStatus != null) {
            products = productRepository.findByPromotionAndStockStatus(stockStatus, pageable);
        } else if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId, pageable);
        } else if (promotion != null && promotion) {
            products = productRepository.findByPromotionTrue(pageable);
        } else if (stockStatus != null) {
            products = productRepository.findByStockStatus(stockStatus, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }
        
        return products.map(ProductDTO::fromEntity);
    }
    
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return ProductDTO.fromEntity(product);
    }
    
    @Transactional
    public ProductDTO createProduct(ProductCreateDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        
        Product product = new Product();
        product.setNom(dto.getNom());
        product.setDescription(dto.getDescription());
        product.setPrix(dto.getPrix());
        product.setCategory(category);
        product.setStockStatus(dto.getStockStatus());
        product.setPromotion(dto.getPromotion() != null ? dto.getPromotion() : false);
        
        product = productRepository.save(product);
        return ProductDTO.fromEntity(product);
    }
    
    @Transactional
    public ProductDTO updateProduct(Long id, ProductCreateDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        
        product.setNom(dto.getNom());
        product.setDescription(dto.getDescription());
        product.setPrix(dto.getPrix());
        product.setCategory(category);
        product.setStockStatus(dto.getStockStatus());
        product.setPromotion(dto.getPromotion() != null ? dto.getPromotion() : false);
        
        product = productRepository.save(product);
        return ProductDTO.fromEntity(product);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        productRepository.delete(product);
    }
}
