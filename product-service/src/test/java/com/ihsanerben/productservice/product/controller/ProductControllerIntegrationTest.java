package com.ihsanerben.productservice.product.controller;

import com.ihsanerben.productservice.TestcontainersConfiguration;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateListAndRetrieveProduct() throws Exception {
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sku": "sku-001",
                                  "name": "Mechanical Keyboard",
                                  "description": "Compact mechanical keyboard",
                                  "price": 1499.90
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value("SKU-001"))
                .andReturn().getResponse().getContentAsString();

        String productId = response.replaceAll(".*\\\"id\\\":(\\d+).*", "$1");

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mechanical Keyboard"));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value("SKU-001"));
    }

    @Test
    void shouldRejectInvalidProduct() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sku": "",
                                  "name": "",
                                  "price": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.sku").exists())
                .andExpect(jsonPath("$.fieldErrors.name").exists())
                .andExpect(jsonPath("$.fieldErrors.price").exists());
    }
}
