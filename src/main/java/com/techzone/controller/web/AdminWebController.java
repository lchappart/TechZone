package com.techzone.controller.web;

import com.techzone.dto.CategoryDTO;
import com.techzone.dto.OrderDTO;
import com.techzone.dto.ProductDTO;
import com.techzone.entity.Order;
import com.techzone.entity.Product;
import com.techzone.repository.CategoryRepository;
import com.techzone.repository.OrderRepository;
import com.techzone.repository.ProductRepository;
import com.techzone.service.CategoryService;
import com.techzone.service.OrderService;
import com.techzone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminWebController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalProducts = productRepository.count();
        long totalCategories = categoryRepository.count();
        long totalOrders = orderRepository.count();
        long pendingOrders = orderRepository.findAll().stream()
                .filter(o -> o.getStatut() == Order.OrderStatus.EN_ATTENTE)
                .count();
        
        model.addAttribute("title", "Tableau de bord Admin - TechZone");
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("pendingOrders", pendingOrders);
        return "admin/dashboard";
    }
    
    @GetMapping("/products")
    public String products(@RequestParam(defaultValue = "0") int page,
                          Model model) {
        Page<ProductDTO> products = productService.getAllProducts(page, 20, null, null, null);
        List<CategoryDTO> categories = categoryService.getAllCategories();
        
        model.addAttribute("title", "Gestion Produits - TechZone");
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "admin/products";
    }
    
    @GetMapping("/categories")
    public String categories(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("title", "Gestion Catégories - TechZone");
        model.addAttribute("categories", categories);
        return "admin/categories";
    }
    
    @GetMapping("/orders")
    public String orders(@RequestParam(defaultValue = "0") int page,
                        Model model) {
        Page<OrderDTO> orders = orderService.getAllOrders(page, 20);
        
        model.addAttribute("title", "Gestion Commandes - TechZone");
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        return "admin/orders";
    }
    
    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        OrderDTO order = orderService.getOrderById(id);
        model.addAttribute("title", "Détail Commande #" + id + " - TechZone");
        model.addAttribute("order", order);
        return "admin/order-detail";
    }
    
    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id,
                                   @RequestParam Order.OrderStatus status,
                                   RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(id, status);
        redirectAttributes.addFlashAttribute("message", "Statut de la commande mis à jour");
        return "redirect:/admin/orders/" + id;
    }
}
