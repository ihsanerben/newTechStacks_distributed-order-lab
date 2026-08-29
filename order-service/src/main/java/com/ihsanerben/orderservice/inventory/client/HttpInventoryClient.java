package com.ihsanerben.orderservice.inventory.client;

import com.ihsanerben.orderservice.inventory.dto.ReserveStockRequest;
import com.ihsanerben.orderservice.inventory.exception.InventoryReservationRejectedException;
import com.ihsanerben.orderservice.inventory.exception.InventoryServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Component
public class HttpInventoryClient implements InventoryClient {
    private final RestClient restClient;

    public HttpInventoryClient(@Value("${clients.inventory.base-url}") String inventoryBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(inventoryBaseUrl)
                .build();
    }

    @Override
    public void reserveStock(Long productId, Integer quantity) {
        try {
            restClient.post()
                    .uri("/api/inventory/products/{productId}/reservations", productId)
                    .body(new ReserveStockRequest(quantity))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode().is4xxClientError()) {
                throw new InventoryReservationRejectedException(productId);
            }
            throw new InventoryServiceUnavailableException();
        } catch (RestClientException exception) {
            throw new InventoryServiceUnavailableException();
        }
    }
}
