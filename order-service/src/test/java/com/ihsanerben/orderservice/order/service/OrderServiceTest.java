package com.ihsanerben.orderservice.order.service;

import com.ihsanerben.orderservice.order.dto.CreateOrderRequest;
import com.ihsanerben.orderservice.order.dto.OrderResponse;
import com.ihsanerben.orderservice.order.entity.CustomerOrder;
import com.ihsanerben.orderservice.order.entity.OrderStatus;
import com.ihsanerben.orderservice.order.exception.OrderNotFoundException;
import com.ihsanerben.orderservice.order.mapper.OrderMapper;
import com.ihsanerben.orderservice.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, new OrderMapper());
    }

    @Test
    void shouldCreateOrder() {
        CreateOrderRequest request = new CreateOrderRequest(10L, 2, "USER@example.com");
        when(orderRepository.save(any(CustomerOrder.class))).thenAnswer(invocation -> {
            CustomerOrder order = invocation.getArgument(0);
            return CustomerOrder.builder()
                    .id(1L)
                    .productId(order.getProductId())
                    .quantity(order.getQuantity())
                    .customerEmail(order.getCustomerEmail())
                    .status(order.getStatus())
                    .createdAt(order.getCreatedAt())
                    .build();
        });

        OrderResponse response = orderService.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.customerEmail()).isEqualTo("user@example.com");
        assertThat(response.status()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    void shouldThrowWhenOrderDoesNotExist() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.findById(99L))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessage("Order not found with id: 99");
    }
}
