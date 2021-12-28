package com.feliper.userservice.service;

import com.feliper.userservice.dto.TransactionRequestDto;
import com.feliper.userservice.dto.TransactionResponseDto;
import com.feliper.userservice.dto.TransactionStatus;
import com.feliper.userservice.dto.UserTransactionDto;
import com.feliper.userservice.repository.UserRepository;
import com.feliper.userservice.repository.UserTransactionRepository;
import com.feliper.userservice.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final UserRepository userRepository;

    private final TransactionalOperator transactionalOperator;

    private final UserTransactionRepository userTransactionRepository;

    public TransactionService(UserRepository userRepository, TransactionalOperator transactionalOperator,
                              UserTransactionRepository userTransactionRepository) {
        this.userRepository = userRepository;
        this.transactionalOperator = transactionalOperator;
        this.userTransactionRepository = userTransactionRepository;
    }

    public Mono<TransactionResponseDto> createTransaction(Mono<TransactionRequestDto> requestDto) {

        return requestDto.flatMap(transactionRequest ->
                this.userRepository.updateUserBalance(transactionRequest.getUserId(), transactionRequest.getAmount())
                        .filter(Boolean::booleanValue)
                        .map(aBoolean -> EntityDtoUtil.toEntity(transactionRequest))
                        .flatMap(this.userTransactionRepository::save)
                        .map(userTransaction -> EntityDtoUtil.toDto(transactionRequest, TransactionStatus.APPROVED))
                        .as(transactionalOperator::transactional)
                        .defaultIfEmpty(EntityDtoUtil.toDto(transactionRequest, TransactionStatus.DECLINED)));
    }

    public Flux<UserTransactionDto> getByUserId(Integer userId) {
        return this.userTransactionRepository.findByUserId(userId).map(EntityDtoUtil::toDto);
    }
}
