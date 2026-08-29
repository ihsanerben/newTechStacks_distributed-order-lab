package com.ihsanerben.orderservice.inventory.client;

public interface InventoryClient {
    void reserveStock(Long productId, Integer quantity);
}
