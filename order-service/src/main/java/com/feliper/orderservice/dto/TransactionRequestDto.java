package com.feliper.orderservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Builder
public class TransactionRequestDto {

    private Integer userId;
    private Integer amount;

}
