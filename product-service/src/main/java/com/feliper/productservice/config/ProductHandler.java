package com.feliper.productservice.config;

import com.feliper.productservice.dto.ProductDto;
import com.feliper.productservice.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductHandler {

    private final ProductService productService;

    private final Flux<ProductDto> productBroadcast;

    public ProductHandler(ProductService productService, Flux<ProductDto> productBroadcast) {
        this.productService = productService;
        this.productBroadcast = productBroadcast;
    }

    public Mono<ServerResponse> getAllHandler(ServerRequest request) {
        Flux<ProductDto> productDtoFlux = this.productService.getAll();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productDtoFlux, ProductDto.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return this.productService.getProductById(id)
                .flatMap(productDto -> ServerResponse.ok().body(Mono.just(productDto), ProductDto.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> insertProduct(ServerRequest request) {
        Mono<ProductDto> requestDtoMono = request.bodyToMono(ProductDto.class);
        Mono<ProductDto> productDtoMono = this.productService.insertProduct(requestDtoMono);
        return ServerResponse.ok()
                .body(productDtoMono, ProductDto.class);
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ProductDto> requestDtoMono = request.bodyToMono(ProductDto.class);
        return this.productService.updateProduct(id, requestDtoMono)
                .flatMap(productDto -> ServerResponse.ok().body(Mono.just(productDto), ProductDto.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> voidMono = this.productService.deleteProductById(id);
        return ServerResponse.noContent().build(voidMono);
    }

    public Mono<ServerResponse> findByPriceRange(ServerRequest request) {
        int min = Integer.valueOf(request.queryParams().getFirst("min"));
        int max = Integer.valueOf(request.queryParams().getFirst("max"));
        Flux<ProductDto> productDtoFlux = this.productService.findByPriceRange(min, max);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productDtoFlux, ProductDto.class);
    }

    public Mono<ServerResponse> streamProduct(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productBroadcast, ProductDto.class);
    }

}
