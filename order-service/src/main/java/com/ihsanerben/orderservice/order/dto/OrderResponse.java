package com.ihsanerben.orderservice.order.dto;

import com.ihsanerben.orderservice.order.entity.OrderStatus;

import java.time.Instant;

public record OrderResponse(
        Long id,
        Long productId,
        Integer quantity,
        String customerEmail,
        OrderStatus status,
        Instant createdAt) {
}
