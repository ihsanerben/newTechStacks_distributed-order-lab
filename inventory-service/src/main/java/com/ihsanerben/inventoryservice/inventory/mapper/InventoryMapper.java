package com.ihsanerben.inventoryservice.inventory.mapper;

import com.ihsanerben.inventoryservice.inventory.dto.InventoryResponse;
import com.ihsanerben.inventoryservice.inventory.entity.InventoryProduct;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {
    public InventoryResponse toResponse(InventoryProduct product) {
        return new InventoryResponse(
                product.getId(), product.getSku(), product.getName(),
                product.getAvailableQuantity(), product.getVersion(),
                product.getCreatedAt(), product.getUpdatedAt());
    }
}
