package com.samsung.s28utra.productservice.application.mapper;

import com.samsung.s28utra.productservice.application.dto.CreateProductRequest;
import com.samsung.s28utra.productservice.application.dto.ProductDTO;
import com.samsung.s28utra.productservice.application.dto.UpdateProductRequest;
import com.samsung.s28utra.productservice.domain.entity.Product;

import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    ProductDTO toDTO(Product product);
    List<ProductDTO> toDTOList(List<Product> products);
    Product toEntity(CreateProductRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(UpdateProductRequest request, @MappingTarget Product product);

//    @Mapping(target = "createdAt", expression = "java(formatDateTime(product.getCreatedAt()))")
//    @Mapping(target = "updatedAt", expression = "java(formatDateTime(product.getUpdatedAt()))")
//    @Mapping(target = "price", expression = "java(product.getPrice().doubleValue())")
//    ProductResponse toGrpcResponse(Product product);

    default String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(FORMATTER) : "";
    }
}
