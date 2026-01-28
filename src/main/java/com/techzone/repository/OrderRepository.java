package com.techzone.repository;

import com.techzone.entity.Order;
import com.techzone.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByDateDesc(User user);
    Page<Order> findByUserOrderByDateDesc(User user, Pageable pageable);
    Page<Order> findAllByOrderByDateDesc(Pageable pageable);
}
