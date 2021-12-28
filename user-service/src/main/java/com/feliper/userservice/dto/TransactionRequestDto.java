package com.feliper.userservice.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class TransactionRequestDto {

    private Integer userId;
    private Integer amount;

}
