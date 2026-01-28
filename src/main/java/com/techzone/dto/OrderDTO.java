package com.techzone.dto;

import com.techzone.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime date;
    private Order.OrderStatus statut;
    private BigDecimal total;
    private Long userId;
    private String userEmail;
    private List<OrderLineDTO> orderLines = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderLineDTO {
        private Long id;
        private Integer quantity;
        private BigDecimal prixUnitaire;
        private Long productId;
        private String productName;
    }
    
    public static OrderDTO fromEntity(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setDate(order.getDate());
        dto.setStatut(order.getStatut());
        dto.setTotal(order.getTotal());
        dto.setUserId(order.getUser().getId());
        dto.setUserEmail(order.getUser().getEmail());
        
        order.getOrderLines().forEach(line -> {
            OrderLineDTO lineDTO = new OrderLineDTO();
            lineDTO.setId(line.getId());
            lineDTO.setQuantity(line.getQuantity());
            lineDTO.setPrixUnitaire(line.getPrixUnitaire());
            lineDTO.setProductId(line.getProduct().getId());
            lineDTO.setProductName(line.getProduct().getNom());
            dto.getOrderLines().add(lineDTO);
        });
        
        return dto;
    }
}
