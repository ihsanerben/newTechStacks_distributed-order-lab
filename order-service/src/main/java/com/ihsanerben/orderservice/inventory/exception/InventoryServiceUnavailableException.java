package com.ihsanerben.orderservice.inventory.exception;

public class InventoryServiceUnavailableException extends RuntimeException {
    public InventoryServiceUnavailableException() {
        super("Inventory service is currently unavailable.");
    }
}
