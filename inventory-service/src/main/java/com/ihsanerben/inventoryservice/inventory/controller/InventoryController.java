package com.ihsanerben.inventoryservice.inventory.controller;

import com.ihsanerben.inventoryservice.inventory.dto.CreateProductRequest;
import com.ihsanerben.inventoryservice.inventory.dto.InventoryResponse;
import com.ihsanerben.inventoryservice.inventory.dto.UpdateStockRequest;
import com.ihsanerben.inventoryservice.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory/products")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse create(@Valid @RequestBody CreateProductRequest request) {
        return inventoryService.create(request);
    }

    @GetMapping("/{productId}")
    public InventoryResponse findById(@PathVariable Long productId) {
        return inventoryService.findById(productId);
    }

    @PutMapping("/{productId}/stock")
    public InventoryResponse updateStock(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateStockRequest request) {
        return inventoryService.updateStock(productId, request);
    }
}
