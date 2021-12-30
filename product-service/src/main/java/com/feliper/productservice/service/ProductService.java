package com.feliper.productservice.service;

import com.feliper.productservice.dto.ProductDto;
import com.feliper.productservice.repository.ProductRepository;
import com.feliper.productservice.util.EntityDtoUtil;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final Sinks.Many<ProductDto> sink;

    public ProductService(ProductRepository productRepository, Sinks.Many<ProductDto> sink) {
        this.productRepository = productRepository;
        this.sink = sink;
    }

    public Flux<ProductDto> getAll() {
        return this.productRepository.findAll().map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return this.productRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.productRepository::insert)
                .map(EntityDtoUtil::toDto)
                .doOnNext(this.sink::tryEmitNext); 
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return this.productRepository.findById(id)
                .flatMap(v -> productDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(product -> product.setId(id)))
                .flatMap(this.productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProductById(String id) {
        return this.productRepository.deleteById(id);
    }

    public Flux<ProductDto> findByPriceRange(int min, int max) {
        return this.productRepository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }
}
