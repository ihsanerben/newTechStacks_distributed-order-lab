package com.ihsanerben.inventoryservice.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
        @NotBlank @Size(max = 50) String sku,
        @NotBlank @Size(max = 120) String name,
        @NotNull @PositiveOrZero Integer initialQuantity) {
}
