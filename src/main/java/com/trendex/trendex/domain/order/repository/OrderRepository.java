package com.trendex.trendex.domain.order.repository;

import com.trendex.trendex.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
