package com.ihsanerben.productservice.product.mapper;

import com.ihsanerben.productservice.product.dto.ProductResponse;
import com.ihsanerben.productservice.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(), product.getSku(), product.getName(), product.getDescription(),
                product.getPrice(), product.isActive(), product.getCreatedAt());
    }
}
