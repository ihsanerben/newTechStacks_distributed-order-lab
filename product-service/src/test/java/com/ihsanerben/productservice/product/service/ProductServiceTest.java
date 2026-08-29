package com.ihsanerben.productservice.product.service;

import com.ihsanerben.productservice.product.dto.CreateProductRequest;
import com.ihsanerben.productservice.product.dto.ProductResponse;
import com.ihsanerben.productservice.product.entity.Product;
import com.ihsanerben.productservice.product.exception.DuplicateSkuException;
import com.ihsanerben.productservice.product.mapper.ProductMapper;
import com.ihsanerben.productservice.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, new ProductMapper());
    }

    @Test
    void shouldCreateProduct() {
        when(productRepository.existsBySkuIgnoreCase("SKU-001")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(call -> call.getArgument(0));
        ProductResponse response = productService.create(new CreateProductRequest(
                " sku-001 ", "Keyboard", "Mechanical keyboard", new BigDecimal("1499.90")));
        assertThat(response.sku()).isEqualTo("SKU-001");
        assertThat(response.active()).isTrue();
    }

    @Test
    void shouldRejectDuplicateSku() {
        when(productRepository.existsBySkuIgnoreCase("SKU-001")).thenReturn(true);
        assertThatThrownBy(() -> productService.create(new CreateProductRequest(
                "sku-001", "Keyboard", null, BigDecimal.TEN)))
                .isInstanceOf(DuplicateSkuException.class);
    }
}
