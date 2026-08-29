package com.ihsanerben.productservice.product.service;

import com.ihsanerben.productservice.product.dto.CreateProductRequest;
import com.ihsanerben.productservice.product.dto.ProductResponse;
import com.ihsanerben.productservice.product.entity.Product;
import com.ihsanerben.productservice.product.exception.DuplicateSkuException;
import com.ihsanerben.productservice.product.exception.ProductNotFoundException;
import com.ihsanerben.productservice.product.mapper.ProductMapper;
import com.ihsanerben.productservice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse create(CreateProductRequest request) {
        String sku = request.sku().trim().toUpperCase();
        if (productRepository.existsBySkuIgnoreCase(sku)) {
            throw new DuplicateSkuException(sku);
        }
        Product product = Product.builder()
                .sku(sku)
                .name(request.name().trim())
                .description(request.description() == null ? null : request.description().trim())
                .price(request.price())
                .active(true)
                .createdAt(Instant.now())
                .build();
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(productMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toResponse(product);
    }
}
