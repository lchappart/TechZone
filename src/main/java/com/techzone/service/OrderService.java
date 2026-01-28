package com.techzone.service;

import com.techzone.dto.CartItemDTO;
import com.techzone.dto.OrderDTO;
import com.techzone.entity.Order;
import com.techzone.entity.OrderLine;
import com.techzone.entity.Product;
import com.techzone.entity.User;
import com.techzone.repository.OrderRepository;
import com.techzone.repository.ProductRepository;
import com.techzone.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Transactional
    public OrderDTO createOrder(List<CartItemDTO> cartItems, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setStatut(Order.OrderStatus.EN_ATTENTE);
        order.setUser(user);
        order.setTotal(BigDecimal.ZERO);
        
        BigDecimal total = BigDecimal.ZERO;
        
        for (CartItemDTO item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé: " + item.getProductId()));
            
            if (product.getStockStatus() == Product.StockStatus.RUPTURE) {
                throw new RuntimeException("Le produit " + product.getNom() + " est en rupture de stock");
            }
            
            OrderLine orderLine = new OrderLine();
            orderLine.setOrder(order);
            orderLine.setProduct(product);
            orderLine.setQuantity(item.getQuantity());
            orderLine.setPrixUnitaire(product.getPrix());
            
            BigDecimal lineTotal = product.getPrix().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(lineTotal);
            
            order.getOrderLines().add(orderLine);
        }
        
        order.setTotal(total);
        order = orderRepository.save(order);
        
        logger.info("Commande créée: ID={}, User={}, Total={}", order.getId(), user.getEmail(), order.getTotal());
        
        return OrderDTO.fromEntity(order);
    }
    
    public List<OrderDTO> getUserOrders(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        return orderRepository.findByUserOrderByDateDesc(user).stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public Page<OrderDTO> getUserOrders(String userEmail, int page, int size) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByUserOrderByDateDesc(user, pageable)
                .map(OrderDTO::fromEntity);
    }
    
    public OrderDTO getOrderById(Long id, String userEmail) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        
        if (!order.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Accès non autorisé à cette commande");
        }
        
        return OrderDTO.fromEntity(order);
    }
    
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public Page<OrderDTO> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAllByOrderByDateDesc(pageable)
                .map(OrderDTO::fromEntity);
    }
    
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        return OrderDTO.fromEntity(order);
    }
    
    @Transactional
    public OrderDTO updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        order.setStatut(status);
        order = orderRepository.save(order);
        logger.info("Statut de la commande {} mis à jour: {}", order.getId(), status);
        return OrderDTO.fromEntity(order);
    }
}
