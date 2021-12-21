package com.feliper.webfluxdemo.config;

import com.feliper.webfluxdemo.dto.InputFailedValidationResponse;
import com.feliper.webfluxdemo.dto.exception.InputValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    private final RequestHandler handler;

    public RouterConfig(RequestHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", RequestPredicates.path("*/1?"), handler::squareHandler)
                .GET("square/{input}", req -> ServerResponse.badRequest().bodyValue("only 10-19 allowed"))
                .GET("square/{input}", handler::squareHandler)
                .GET("table/{input}", handler::tableHandler)
                .GET("table/{input}/stream", handler::tableStreamHandler)
                .POST("multiply", handler::multiplyHandler)
                .GET("square/{input}/validation", handler::squareValidationHandler)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    public BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            InputValidationException ex = (InputValidationException) err;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setMessage(ex.getMessage());
            response.setInput(ex.getInput());
            response.setErrorCode(ex.getErrorCode());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }

}
