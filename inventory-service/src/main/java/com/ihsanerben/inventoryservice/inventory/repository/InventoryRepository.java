package com.ihsanerben.inventoryservice.inventory.repository;

import com.ihsanerben.inventoryservice.inventory.entity.InventoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryProduct, Long> {
    boolean existsBySkuIgnoreCase(String sku);
}
