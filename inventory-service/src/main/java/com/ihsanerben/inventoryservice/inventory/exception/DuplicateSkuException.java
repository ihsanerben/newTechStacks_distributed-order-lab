package com.ihsanerben.inventoryservice.inventory.exception;

public class DuplicateSkuException extends RuntimeException {
    public DuplicateSkuException(String sku) {
        super("Inventory product already exists with SKU: " + sku);
    }
}
