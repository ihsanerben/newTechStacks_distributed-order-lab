package com.ihsanerben.inventoryservice.inventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "inventory_products")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryProduct {
    @Id
    private Long productId;
    private Integer availableQuantity;
    @Version
    private Long version;
    private Instant createdAt;
    private Instant updatedAt;

    public void updateStock(Integer quantity, Instant updatedAt) {
        this.availableQuantity = quantity;
        this.updatedAt = updatedAt;
    }

    public void reserve(Integer quantity, Instant updatedAt) {
        this.availableQuantity -= quantity;
        this.updatedAt = updatedAt;
    }
}
