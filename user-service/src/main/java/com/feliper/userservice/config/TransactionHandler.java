package com.feliper.userservice.config;

import com.feliper.userservice.dto.TransactionRequestDto;
import com.feliper.userservice.dto.TransactionResponseDto;
import com.feliper.userservice.dto.UserTransactionDto;
import com.feliper.userservice.service.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionHandler {

    private final TransactionService transactionService;

    public TransactionHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<TransactionRequestDto> requestDtoMono = request.bodyToMono(TransactionRequestDto.class);
        Mono<TransactionResponseDto> responseDtoMono = this.transactionService.createTransaction(requestDtoMono);
        return ServerResponse.ok()
                .body(responseDtoMono, TransactionResponseDto.class);
    }

    public Mono<ServerResponse> getByUserId(ServerRequest request) {
        int userId = Integer.valueOf(request.queryParams().getFirst("userId"));
        Flux<UserTransactionDto> userTransactionDtoFlux = this.transactionService.getByUserId(userId);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userTransactionDtoFlux, UserTransactionDto.class);
    }

}
