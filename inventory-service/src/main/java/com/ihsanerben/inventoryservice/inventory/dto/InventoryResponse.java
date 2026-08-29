package com.ihsanerben.inventoryservice.inventory.dto;

import java.time.Instant;

public record InventoryResponse(
        Long id, String sku, String name, Integer availableQuantity,
        Long version, Instant createdAt, Instant updatedAt) {
}
