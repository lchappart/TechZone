package com.techzone.controller.api;

import com.techzone.dto.CartItemDTO;
import com.techzone.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCart(HttpServletRequest request) {
        return ResponseEntity.ok(cartService.getCart(request));
    }
    
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            HttpServletRequest request,
            HttpServletResponse response) {
        cartService.addToCart(productId, quantity, request, response);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable Long productId,
            HttpServletRequest request,
            HttpServletResponse response) {
        cartService.removeFromCart(productId, request, response);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/update")
    public ResponseEntity<Void> updateCartQuantity(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            HttpServletRequest request,
            HttpServletResponse response) {
        cartService.updateCartQuantity(productId, quantity, request, response);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(HttpServletResponse response) {
        cartService.clearCart(response);
        return ResponseEntity.ok().build();
    }
}
