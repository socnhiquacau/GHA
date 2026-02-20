package com.samsung.s28utra.productservice.application.service.impl;

import com.samsung.s28utra.productservice.application.dto.CreateProductRequest;
import com.samsung.s28utra.productservice.application.dto.ProductDTO;
import com.samsung.s28utra.productservice.application.dto.UpdateProductRequest;
import com.samsung.s28utra.productservice.application.mapper.ProductMapper;
import com.samsung.s28utra.productservice.application.service.ProductService;
import com.samsung.s28utra.productservice.domain.entity.Product;
import com.samsung.s28utra.productservice.domain.repository.ProductRepository;
import com.samsung.s28utra.productservice.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final Tracer tracer;

    @Override
    public ProductDTO createProduct(CreateProductRequest request) {
        Span span = tracer.spanBuilder("ProductService.createProduct").startSpan();
        try {
            log.info("Creating new product: {}", request.getName());
            Product product = productMapper.toEntity(request);
            Product savedProduct = productRepository.save(product);
            log.info("Product created successfully with id: {}", savedProduct.getId());
            return productMapper.toDTO(savedProduct);
        } finally {
            span.end();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Span span = tracer.spanBuilder("ProductService.getProductById").startSpan();
        try {
            log.debug("Fetching product with id: {}", id);
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            return productMapper.toDTO(product);
        } finally {
            span.end();
        }
    }

    @Override
    public ProductDTO updateProduct(Long id, UpdateProductRequest request) {
        Span span = tracer.spanBuilder("ProductService.updateProduct").startSpan();
        try {
            log.info("Updating product with id: {}", id);
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            productMapper.updateEntityFromRequest(request, product);
            Product updatedProduct = productRepository.save(product);
            log.info("Product updated successfully with id: {}", updatedProduct.getId());
            return productMapper.toDTO(updatedProduct);
        } finally {
            span.end();
        }
    }

    @Override
    public void deleteProduct(Long id) {
        Span span = tracer.spanBuilder("ProductService.deleteProduct").startSpan();
        try {
            log.info("Deleting product with id: {}", id);
            if (!productRepository.existsById(id)) {
                throw new ResourceNotFoundException("Product not found with id: " + id);
            }
            productRepository.deleteById(id);
            log.info("Product deleted successfully with id: {}", id);
        } finally {
            span.end();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Span span = tracer.spanBuilder("ProductService.getAllProducts").startSpan();
        try {
            log.debug("Fetching all products with pagination");
            Page<Product> products = productRepository.findAll(pageable);
            return products.map(productMapper::toDTO);
        } finally {
            span.end();
        }
    }
}
