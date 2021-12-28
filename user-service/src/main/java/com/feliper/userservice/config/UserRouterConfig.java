package com.feliper.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouterConfig {

    private final UserHandler handler;

    public UserRouterConfig(UserHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> userRouterFunction() {
        return RouterFunctions.route()
                .path("user", this::buildUserRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> buildUserRouterFunction() {
        return RouterFunctions.route()
                .GET("all", handler::getAllHandler)
                .GET("{id}", handler::getById)
                .POST("", handler::create)
                .PUT("{id}", handler::update)
                .DELETE("{id}", handler::delete)
                .build();
    }
}
