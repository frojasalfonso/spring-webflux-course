package com.feliper.orderservice.client;

import com.feliper.orderservice.dto.TransactionRequestDto;
import com.feliper.orderservice.dto.TransactionResponseDto;
import com.feliper.orderservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private WebClient webClient;

    public UserClient(@Value("${user.service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto requestDto) {
        return  this.webClient.post()
                .uri("transaction")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }

    public Flux<UserDto> getAllUser() {
        return this.webClient.get()
                .uri("all")
                .retrieve()
                .bodyToFlux(UserDto.class);
    }
}
