package com.sparsh.orderservice.repository;

import com.sparsh.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderNumberIs(String orderNumber);
}
