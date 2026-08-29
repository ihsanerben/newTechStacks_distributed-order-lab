package com.ihsanerben.inventoryservice.inventory.service;

import com.ihsanerben.inventoryservice.inventory.dto.CreateProductRequest;
import com.ihsanerben.inventoryservice.inventory.dto.InventoryResponse;
import com.ihsanerben.inventoryservice.inventory.dto.ReserveStockRequest;
import com.ihsanerben.inventoryservice.inventory.dto.UpdateStockRequest;
import com.ihsanerben.inventoryservice.inventory.entity.InventoryProduct;
import com.ihsanerben.inventoryservice.inventory.exception.DuplicateSkuException;
import com.ihsanerben.inventoryservice.inventory.exception.InventoryProductNotFoundException;
import com.ihsanerben.inventoryservice.inventory.exception.InsufficientStockException;
import com.ihsanerben.inventoryservice.inventory.mapper.InventoryMapper;
import com.ihsanerben.inventoryservice.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Transactional
    public InventoryResponse create(CreateProductRequest request) {
        String normalizedSku = request.sku().trim().toUpperCase();
        if (inventoryRepository.existsBySkuIgnoreCase(normalizedSku)) {
            throw new DuplicateSkuException(normalizedSku);
        }
        Instant now = Instant.now();
        InventoryProduct product = InventoryProduct.builder()
                .sku(normalizedSku)
                .name(request.name().trim())
                .availableQuantity(request.initialQuantity())
                .createdAt(now)
                .updatedAt(now)
                .build();
        return inventoryMapper.toResponse(inventoryRepository.save(product));
    }

    @Transactional(readOnly = true)
    public InventoryResponse findById(Long productId) {
        return inventoryMapper.toResponse(findProduct(productId));
    }

    @Transactional
    public InventoryResponse updateStock(Long productId, UpdateStockRequest request) {
        InventoryProduct product = findProduct(productId);
        product.updateStock(request.quantity(), Instant.now());
        return inventoryMapper.toResponse(inventoryRepository.save(product));
    }

    @Transactional
    public InventoryResponse reserveStock(Long productId, ReserveStockRequest request) {
        InventoryProduct product = findProduct(productId);
        if (product.getAvailableQuantity() < request.quantity()) {
            throw new InsufficientStockException(
                    productId, request.quantity(), product.getAvailableQuantity());
        }
        product.reserve(request.quantity(), Instant.now());
        return inventoryMapper.toResponse(inventoryRepository.save(product));
    }

    private InventoryProduct findProduct(Long productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(() -> new InventoryProductNotFoundException(productId));
    }
}
