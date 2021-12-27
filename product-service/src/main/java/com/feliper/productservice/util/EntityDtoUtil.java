package com.feliper.productservice.util;

import com.feliper.productservice.dto.ProductDto;
import com.feliper.productservice.entity.Product;

public class EntityDtoUtil {

    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public static Product toEntity(ProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
    }

}
