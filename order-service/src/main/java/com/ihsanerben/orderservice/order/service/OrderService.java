package com.ihsanerben.orderservice.order.service;

import com.ihsanerben.orderservice.order.dto.CreateOrderRequest;
import com.ihsanerben.orderservice.inventory.client.InventoryClient;
import com.ihsanerben.orderservice.order.dto.OrderResponse;
import com.ihsanerben.orderservice.order.entity.CustomerOrder;
import com.ihsanerben.orderservice.order.entity.OrderStatus;
import com.ihsanerben.orderservice.order.exception.OrderNotFoundException;
import com.ihsanerben.orderservice.order.mapper.OrderMapper;
import com.ihsanerben.orderservice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryClient inventoryClient;

    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        inventoryClient.reserveStock(request.productId(), request.quantity());

        CustomerOrder order = CustomerOrder.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .customerEmail(request.customerEmail().trim().toLowerCase())
                .status(OrderStatus.CREATED)
                .createdAt(Instant.now())
                .build();

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        CustomerOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return orderMapper.toResponse(order);
    }
}
