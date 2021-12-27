package com.feliper.productservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
@Builder
public class Product {

    @Id
    private String id;
    private String description;
    private Integer price;
}
