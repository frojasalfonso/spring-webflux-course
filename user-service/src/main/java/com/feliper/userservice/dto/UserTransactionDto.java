package com.feliper.userservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@ToString
@Data
@Builder
public class UserTransactionDto {

    private Integer id;
    private Integer userId;
    private Integer amount;
    private LocalDateTime transactionDate;

}
