package com.samsung.s28utra.productservice.presentation.grpc;

import com.samsung.s28utra.productservice.application.dto.CreateProductRequest;
import com.samsung.s28utra.productservice.application.dto.ProductDTO;
import com.samsung.s28utra.productservice.application.dto.UpdateProductRequest;
import com.samsung.s28utra.productservice.application.mapper.ProductMapper;
import com.samsung.s28utra.productservice.application.service.ProductService;
import com.samsung.s28utra.productservice.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Override
    public void createProduct(com.samsung.s28utra.productservice.grpc.CreateProductRequest request,
                              StreamObserver<ProductResponse> responseObserver) {
        log.info("gRPC: Creating product: {}", request.getName());
        try {
            CreateProductRequest dto = CreateProductRequest.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(BigDecimal.valueOf(request.getPrice()))
                    .quantity(request.getQuantity())
                    .build();

            ProductDTO product = productService.createProduct(dto);
            ProductResponse response = ProductResponse.newBuilder()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setDescription(product.getDescription() != null ? product.getDescription() : "")
                    .setPrice(product.getPrice().doubleValue())
                    .setQuantity(product.getQuantity())
                    .setCreatedAt(product.getCreatedAt().toString())
                    .setUpdatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : "")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error creating product via gRPC", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        log.info("gRPC: Getting product with id: {}", request.getId());
        try {
            ProductDTO product = productService.getProductById(request.getId());
            ProductResponse response = ProductResponse.newBuilder()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setDescription(product.getDescription() != null ? product.getDescription() : "")
                    .setPrice(product.getPrice().doubleValue())
                    .setQuantity(product.getQuantity())
                    .setCreatedAt(product.getCreatedAt().toString())
                    .setUpdatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : "")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error getting product via gRPC", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateProduct(com.samsung.s28utra.productservice.grpc.UpdateProductRequest request,
                              StreamObserver<ProductResponse> responseObserver) {
        log.info("gRPC: Updating product with id: {}", request.getId());
        try {
            UpdateProductRequest dto = UpdateProductRequest.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(BigDecimal.valueOf(request.getPrice()))
                    .quantity(request.getQuantity())
                    .build();

            ProductDTO product = productService.updateProduct(request.getId(), dto);
            ProductResponse response = ProductResponse.newBuilder()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setDescription(product.getDescription() != null ? product.getDescription() : "")
                    .setPrice(product.getPrice().doubleValue())
                    .setQuantity(product.getQuantity())
                    .setCreatedAt(product.getCreatedAt().toString())
                    .setUpdatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : "")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error updating product via gRPC", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void deleteProduct(DeleteProductRequest request, StreamObserver<DeleteProductResponse> responseObserver) {
        log.info("gRPC: Deleting product with id: {}", request.getId());
        try {
            productService.deleteProduct(request.getId());
            DeleteProductResponse response = DeleteProductResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Product deleted successfully")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error deleting product via gRPC", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void listProducts(ListProductsRequest request, StreamObserver<ListProductsResponse> responseObserver) {
        log.info("gRPC: Listing products - page: {}, size: {}", request.getPage(), request.getSize());
        try {
            Page<ProductDTO> products = productService.getAllProducts(
                    PageRequest.of(request.getPage(), request.getSize())
            );

            ListProductsResponse.Builder responseBuilder = ListProductsResponse.newBuilder()
                    .setTotal(products.getTotalElements());

            products.getContent().forEach(product -> {
                ProductResponse productResponse = ProductResponse.newBuilder()
                        .setId(product.getId())
                        .setName(product.getName())
                        .setDescription(product.getDescription() != null ? product.getDescription() : "")
                        .setPrice(product.getPrice().doubleValue())
                        .setQuantity(product.getQuantity())
                        .setCreatedAt(product.getCreatedAt().toString())
                        .setUpdatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : "")
                        .build();
                responseBuilder.addProducts(productResponse);
            });

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Error listing products via gRPC", e);
            responseObserver.onError(e);
        }
    }
}