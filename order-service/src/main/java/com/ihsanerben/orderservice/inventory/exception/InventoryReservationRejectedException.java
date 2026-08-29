package com.ihsanerben.orderservice.inventory.exception;

public class InventoryReservationRejectedException extends RuntimeException {
    public InventoryReservationRejectedException(Long productId) {
        super("Inventory reservation was rejected for product: " + productId);
    }
}
