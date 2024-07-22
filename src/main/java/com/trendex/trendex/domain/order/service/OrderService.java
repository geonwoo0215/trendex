package com.trendex.trendex.domain.order.service;

import com.trendex.trendex.domain.order.model.Order;
import com.trendex.trendex.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    public final OrderRepository orderRepository;

    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }

}
