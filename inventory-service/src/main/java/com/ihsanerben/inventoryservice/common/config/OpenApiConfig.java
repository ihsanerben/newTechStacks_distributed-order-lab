package com.ihsanerben.inventoryservice.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI inventoryServiceOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Inventory Service API")
                .description("Stok oluşturma, güncelleme ve rezervasyon operasyonları")
                .version("v1"));
    }
}
