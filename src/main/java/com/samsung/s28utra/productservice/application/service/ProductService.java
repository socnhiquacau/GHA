package com.samsung.s28utra.productservice.application.service;

import com.samsung.s28utra.productservice.application.dto.CreateProductRequest;
import com.samsung.s28utra.productservice.application.dto.ProductDTO;
import com.samsung.s28utra.productservice.application.dto.UpdateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDTO createProduct(CreateProductRequest request);
    ProductDTO getProductById(Long id);
    ProductDTO updateProduct(Long id, UpdateProductRequest request);
    void deleteProduct(Long id);
    Page<ProductDTO> getAllProducts(Pageable pageable);
}
