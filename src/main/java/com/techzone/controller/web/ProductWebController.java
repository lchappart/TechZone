package com.techzone.controller.web;

import com.techzone.dto.CategoryDTO;
import com.techzone.dto.ProductDTO;
import com.techzone.entity.Product;
import com.techzone.service.CategoryService;
import com.techzone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductWebController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/products")
    public String products(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean promotion,
            @RequestParam(required = false) String stockStatus,
            Model model) {
        
        Product.StockStatus status = null;
        if (stockStatus != null && !stockStatus.isEmpty()) {
            try {
                status = Product.StockStatus.valueOf(stockStatus);
            } catch (IllegalArgumentException e) {
            }
        }
        
        Page<ProductDTO> products = productService.getAllProducts(page, 12, categoryId, promotion, status);
        List<CategoryDTO> categories = categoryService.getAllCategories();
        
        model.addAttribute("title", "Catalogue - TechZone");
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("promotion", promotion);
        model.addAttribute("stockStatus", stockStatus);
        
        return "products";
    }
    
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("title", product.getNom() + " - TechZone");
        model.addAttribute("product", product);
        return "product-detail";
    }
}
