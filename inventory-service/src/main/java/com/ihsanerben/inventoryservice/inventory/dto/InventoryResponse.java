package com.ihsanerben.inventoryservice.inventory.dto;

import java.time.Instant;

public record InventoryResponse(
        Long productId, Integer availableQuantity,
        Long version, Instant createdAt, Instant updatedAt) {
}
