package com.feliper.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class OrderRouterConfig {

    private final OrderHandler handler;

    public OrderRouterConfig(OrderHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> orderRouterFunction() {
        return RouterFunctions.route()
                .path("order", this::buildOrderRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> buildOrderRouterFunction() {
        return RouterFunctions.route()
                .GET("user/{id}", handler::getByUserId)
                .POST("", handler::create)
                .build();
    }
}
