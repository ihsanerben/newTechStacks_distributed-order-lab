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
        String createResponse = mockMvc.perform(post("/api/inventory/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sku": "sku-001",
                                  "name": "Mechanical Keyboard",
                                  "initialQuantity": 15
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value("SKU-001"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String productId = createResponse.replaceAll(".*\\\"id\\\":(\\d+).*", "$1");

        mockMvc.perform(get("/api/inventory/products/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(15));

        mockMvc.perform(put("/api/inventory/products/{productId}/stock", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\": 25}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableQuantity").value(25));
    }

    @Test
    void shouldRejectInvalidProduct() throws Exception {
        mockMvc.perform(post("/api/inventory/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sku": "",
                                  "name": "",
                                  "initialQuantity": -1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.sku").exists())
                .andExpect(jsonPath("$.fieldErrors.name").exists())
                .andExpect(jsonPath("$.fieldErrors.initialQuantity").exists());
    }
}
