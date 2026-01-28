package com.techzone.controller.web;

import com.techzone.dto.OrderDTO;
import com.techzone.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserWebController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/orders")
    public String orders(@RequestParam(defaultValue = "0") int page,
                        Authentication authentication,
                        Model model) {
        String email = authentication.getName();
        Page<OrderDTO> orders = orderService.getUserOrders(email, page, 20);
        
        model.addAttribute("title", "Mes Commandes - TechZone");
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        return "user/orders";
    }
    
    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Long id,
                             Authentication authentication,
                             Model model) {
        String email = authentication.getName();
        OrderDTO order = orderService.getOrderById(id, email);
        
        model.addAttribute("title", "Détail Commande #" + id + " - TechZone");
        model.addAttribute("order", order);
        return "user/order-detail";
    }
}
