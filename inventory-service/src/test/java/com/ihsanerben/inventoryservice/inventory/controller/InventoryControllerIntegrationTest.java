package com.ihsanerben.inventoryservice.inventory.controller;

import com.ihsanerben.inventoryservice.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class InventoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateRetrieveAndUpdateProductStock() throws Exception {
        mockMvc.perform(post("/api/inventory/products/{productId}", 42L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "initialQuantity": 15
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(42L));

        mockMvc.perform(get("/api/inventory/products/{productId}", 42L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(15));

        mockMvc.perform(put("/api/inventory/products/{productId}/stock", 42L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\": 25}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(25));

        mockMvc.perform(post("/api/inventory/products/{productId}/reservations", 42L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\": 4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(21));
    }

    @Test
    void shouldRejectInvalidInventory() throws Exception {
        mockMvc.perform(post("/api/inventory/products/{productId}", 43L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "initialQuantity": -1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.initialQuantity").exists());
    }

    @Test
    void shouldRejectDuplicateInventory() throws Exception {
        mockMvc.perform(post("/api/inventory/products/{productId}", 44L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"initialQuantity\": 5}"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/inventory/products/{productId}", 44L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"initialQuantity\": 8}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Inventory already exists for product: 44"));
    }
}
