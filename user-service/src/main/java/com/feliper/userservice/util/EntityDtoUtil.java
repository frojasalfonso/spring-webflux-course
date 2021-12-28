package com.feliper.userservice.util;

import com.feliper.userservice.dto.TransactionRequestDto;
import com.feliper.userservice.dto.TransactionResponseDto;
import com.feliper.userservice.dto.TransactionStatus;
import com.feliper.userservice.dto.UserDto;
import com.feliper.userservice.dto.UserTransactionDto;
import com.feliper.userservice.entity.User;
import com.feliper.userservice.entity.UserTransaction;
import java.time.LocalDateTime;

public class EntityDtoUtil {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .balance(user.getBalance())
                .build();
    }

    public static User toEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .balance(dto.getBalance())
                .build();
    }

    public static UserTransaction toEntity(TransactionRequestDto dto) {
        return UserTransaction.builder()
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .transactionDate(LocalDateTime.now() )
                .build();
    }

    public static TransactionResponseDto toDto(TransactionRequestDto dto, TransactionStatus status) {
        return TransactionResponseDto.builder()
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .status(status)
                .build();
    }

    public static UserTransactionDto toDto(UserTransaction entity) {
        return UserTransactionDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .transactionDate(entity.getTransactionDate())
                .build();
    }

}
