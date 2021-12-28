package com.feliper.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TransactionRouterConfig {

    private final TransactionHandler handler;

    public TransactionRouterConfig(TransactionHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> transactionRouterFunction() {
        return RouterFunctions.route()
                .path("user/transaction", this::buildTransactionRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> buildTransactionRouterFunction() {
        return RouterFunctions.route()
                .GET("", handler::getByUserId)
                .POST("", handler::create)
                .build();
    }
}
