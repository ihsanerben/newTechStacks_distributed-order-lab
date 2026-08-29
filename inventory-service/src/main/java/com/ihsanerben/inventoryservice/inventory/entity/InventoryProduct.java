package com.ihsanerben.inventoryservice.inventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "inventory_products", uniqueConstraints =
        @UniqueConstraint(name = "uk_inventory_products_sku", columnNames = "sku"))
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String name;
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
