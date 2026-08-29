package com.ihsanerben.inventoryservice.inventory.service;

import com.ihsanerben.inventoryservice.inventory.dto.CreateProductRequest;
import com.ihsanerben.inventoryservice.inventory.dto.InventoryResponse;
import com.ihsanerben.inventoryservice.inventory.dto.UpdateStockRequest;
import com.ihsanerben.inventoryservice.inventory.entity.InventoryProduct;
import com.ihsanerben.inventoryservice.inventory.exception.DuplicateSkuException;
import com.ihsanerben.inventoryservice.inventory.mapper.InventoryMapper;
import com.ihsanerben.inventoryservice.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {
    @Mock
    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(inventoryRepository, new InventoryMapper());
    }

    @Test
    void shouldCreateProduct() {
        when(inventoryRepository.existsBySkuIgnoreCase("SKU-001")).thenReturn(false);
        when(inventoryRepository.save(any(InventoryProduct.class))).thenAnswer(call -> call.getArgument(0));
        InventoryResponse response = inventoryService.create(
                new CreateProductRequest(" sku-001 ", "Mechanical Keyboard", 15));
        assertThat(response.sku()).isEqualTo("SKU-001");
        assertThat(response.availableQuantity()).isEqualTo(15);
    }

    @Test
    void shouldRejectDuplicateSku() {
        when(inventoryRepository.existsBySkuIgnoreCase("SKU-001")).thenReturn(true);
        assertThatThrownBy(() -> inventoryService.create(
                new CreateProductRequest("sku-001", "Mechanical Keyboard", 15)))
                .isInstanceOf(DuplicateSkuException.class)
                .hasMessage("Inventory product already exists with SKU: SKU-001");
    }

    @Test
    void shouldUpdateStock() {
        InventoryProduct product = InventoryProduct.builder()
                .id(1L).sku("SKU-001").name("Mechanical Keyboard")
                .availableQuantity(15).version(0L)
                .createdAt(Instant.now()).updatedAt(Instant.now()).build();
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(product));
        when(inventoryRepository.save(product)).thenReturn(product);
        InventoryResponse response = inventoryService.updateStock(1L, new UpdateStockRequest(25));
        assertThat(response.availableQuantity()).isEqualTo(25);
    }
}
