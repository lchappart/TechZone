package com.techzone.repository;

import com.techzone.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    
    Page<Product> findByPromotionTrue(Pageable pageable);
    
    Page<Product> findByStockStatus(Product.StockStatus stockStatus, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.promotion = true")
    Page<Product> findByCategoryIdAndPromotion(@Param("categoryId") Long categoryId, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.stockStatus = :stockStatus")
    Page<Product> findByCategoryIdAndStockStatus(@Param("categoryId") Long categoryId, 
                                                   @Param("stockStatus") Product.StockStatus stockStatus, 
                                                   Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.promotion = true AND p.stockStatus = :stockStatus")
    Page<Product> findByPromotionAndStockStatus(@Param("stockStatus") Product.StockStatus stockStatus, 
                                                 Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.promotion = true AND p.stockStatus = :stockStatus")
    Page<Product> findByCategoryIdAndPromotionAndStockStatus(@Param("categoryId") Long categoryId,
                                                              @Param("stockStatus") Product.StockStatus stockStatus,
                                                              Pageable pageable);
    
    List<Product> findByStockStatus(Product.StockStatus stockStatus);
}
