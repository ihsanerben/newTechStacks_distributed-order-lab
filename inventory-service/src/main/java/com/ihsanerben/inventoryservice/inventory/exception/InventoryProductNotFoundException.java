package com.ihsanerben.inventoryservice.inventory.exception;

public class InventoryProductNotFoundException extends RuntimeException {
    public InventoryProductNotFoundException(Long productId) {
        super("Inventory not found for product: " + productId);
    }
}
