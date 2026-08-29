package com.ihsanerben.inventoryservice.inventory.exception;

public class InventoryProductNotFoundException extends RuntimeException {
    public InventoryProductNotFoundException(Long id) {
        super("Inventory product not found with id: " + id);
    }
}
