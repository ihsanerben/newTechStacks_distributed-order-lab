package com.ihsanerben.orderservice.order.repository;

import com.ihsanerben.orderservice.order.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
