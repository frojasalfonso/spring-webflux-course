package com.feliper.orderservice.client;

import com.feliper.orderservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private WebClient webClient;

    public ProductClient(@Value("${product.service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ProductDto> getProductById(String productId) {
        return this.webClient.get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Flux<ProductDto> getAllProduct() {
        return this.webClient.get()
                .uri("all")
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }
}
