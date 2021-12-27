package com.feliper.productservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ProductDto {

    private String id;
    private String description;
    private Integer price;
}
