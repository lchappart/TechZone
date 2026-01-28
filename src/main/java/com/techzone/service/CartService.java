package com.techzone.service;

import com.techzone.dto.CartItemDTO;
import com.techzone.entity.Product;
import com.techzone.repository.ProductRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    
    private static final String CART_COOKIE_NAME = "techzone_cart";
    private static final int COOKIE_MAX_AGE = 7 * 24 * 60 * 60;
    
    @Autowired
    private ProductRepository productRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public List<CartItemDTO> getCart(HttpServletRequest request) {
        Cookie cartCookie = getCartCookie(request);
        if (cartCookie == null) {
            return new ArrayList<>();
        }
        
        try {
            String decodedValue = URLDecoder.decode(cartCookie.getValue(), StandardCharsets.UTF_8.toString());
            return objectMapper.readValue(decodedValue, new TypeReference<List<CartItemDTO>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public void addToCart(Long productId, Integer quantity, HttpServletRequest request, HttpServletResponse response) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        
        if (product.getStockStatus() == Product.StockStatus.RUPTURE) {
            throw new RuntimeException("Ce produit est en rupture de stock");
        }
        
        List<CartItemDTO> cart = getCart(request);
        
        CartItemDTO existingItem = cart.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItemDTO newItem = new CartItemDTO();
            newItem.setProductId(product.getId());
            newItem.setProductName(product.getNom());
            newItem.setPrix(product.getPrix());
            newItem.setQuantity(quantity);
            newItem.setTotal(product.getPrix().multiply(java.math.BigDecimal.valueOf(quantity)));
            newItem.setInStock(product.getStockStatus() == Product.StockStatus.EN_STOCK);
            cart.add(newItem);
        }
        
        updateCartTotal(cart);
        saveCart(cart, response);
    }
    
    public void removeFromCart(Long productId, HttpServletRequest request, HttpServletResponse response) {
        List<CartItemDTO> cart = getCart(request);
        cart.removeIf(item -> item.getProductId().equals(productId));
        updateCartTotal(cart);
        saveCart(cart, response);
    }
    
    public void updateCartQuantity(Long productId, Integer quantity, HttpServletRequest request, HttpServletResponse response) {
        if (quantity <= 0) {
            removeFromCart(productId, request, response);
            return;
        }
        
        List<CartItemDTO> cart = getCart(request);
        CartItemDTO item = cart.stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produit non trouvé dans le panier"));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        
        if (product.getStockStatus() == Product.StockStatus.RUPTURE) {
            throw new RuntimeException("Ce produit est en rupture de stock");
        }
        
        item.setQuantity(quantity);
        item.setPrix(product.getPrix());
        item.setInStock(product.getStockStatus() == Product.StockStatus.EN_STOCK);
        
        updateCartTotal(cart);
        saveCart(cart, response);
    }
    
    public void clearCart(HttpServletResponse response) {
        saveCart(new ArrayList<>(), response);
    }
    
    private void updateCartTotal(List<CartItemDTO> cart) {
        for (CartItemDTO item : cart) {
            item.setTotal(item.getPrix().multiply(java.math.BigDecimal.valueOf(item.getQuantity())));
        }
    }
    
    private void saveCart(List<CartItemDTO> cart, HttpServletResponse response) {
        try {
            String json = objectMapper.writeValueAsString(cart);
            String encodedValue = URLEncoder.encode(json, StandardCharsets.UTF_8.toString());
            
            Cookie cookie = new Cookie(CART_COOKIE_NAME, encodedValue);
            cookie.setPath("/");
            cookie.setMaxAge(COOKIE_MAX_AGE);
            cookie.setHttpOnly(false);
            response.addCookie(cookie);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du panier", e);
        }
    }
    
    private Cookie getCartCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CART_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
    
    public void mergeCartWithUserCart(List<CartItemDTO> guestCart, String userEmail) {
    }
}
