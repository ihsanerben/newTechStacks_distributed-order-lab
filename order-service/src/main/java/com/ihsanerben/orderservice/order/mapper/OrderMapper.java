package com.ihsanerben.orderservice.order.mapper;

import com.ihsanerben.orderservice.order.dto.OrderResponse;
import com.ihsanerben.orderservice.order.entity.CustomerOrder;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(CustomerOrder order) {
        return new OrderResponse(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getCustomerEmail(),
                order.getStatus(),
                order.getCreatedAt());
    }
}
