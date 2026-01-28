package com.techzone.controller.web;

import com.techzone.dto.CartItemDTO;
import com.techzone.dto.OrderDTO;
import com.techzone.service.CartService;
import com.techzone.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartWebController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public String cart(HttpServletRequest request, Model model) {
        List<CartItemDTO> cart = cartService.getCart(request);
        double total = cart.stream()
                .mapToDouble(item -> item.getTotal().doubleValue())
                .sum();
        
        model.addAttribute("title", "Panier - TechZone");
        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);
        return "cart";
    }
    
    @PostMapping("/checkout")
    public String checkout(HttpServletRequest request,
                           HttpServletResponse response,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        if (authentication == null) {
            redirectAttributes.addFlashAttribute("error", "Vous devez être connecté pour passer une commande");
            return "redirect:/login";
        }
        
        List<CartItemDTO> cart = cartService.getCart(request);
        if (cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Votre panier est vide");
            return "redirect:/cart";
        }
        
        try {
            String email = authentication.getName();
            OrderDTO order = orderService.createOrder(cart, email);
            cartService.clearCart(response); // Vide le panier (cookie) après la commande
            redirectAttributes.addFlashAttribute("message", "Commande passée avec succès ! Numéro de commande: " + order.getId());
            return "redirect:/user/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }
}
