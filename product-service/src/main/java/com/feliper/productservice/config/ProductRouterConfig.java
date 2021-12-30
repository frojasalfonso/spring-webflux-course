package com.feliper.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouterConfig {

    private final ProductHandler handler;

    public ProductRouterConfig(ProductHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> productRouterFunction() {
        return RouterFunctions.route()
                .path("product", this::buildProductRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> buildProductRouterFunction() {
        return RouterFunctions.route()
                .GET("stream", handler::streamProduct)
                .GET("all", handler::getAllHandler)
                .GET("price-range", handler::findByPriceRange)
                .GET("{id}", handler::getById)
                .POST("", handler::insertProduct)
                .PUT("{id}", handler::updateProduct)
                .DELETE("{id}", handler::deleteProduct)
                .build();
    }
}
