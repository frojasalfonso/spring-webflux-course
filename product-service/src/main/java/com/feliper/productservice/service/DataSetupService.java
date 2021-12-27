package com.feliper.productservice.service;

import com.feliper.productservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService service;

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 =ProductDto.builder().description("4k-tv").price(1000).build();
        ProductDto p2 = ProductDto.builder().description("slr-camera").price(750).build();
        ProductDto p3 =ProductDto.builder().description("iphone").price(800).build();
        ProductDto p4 =ProductDto.builder().description("headphone").price(100).build();

        Flux.just(p1, p2, p3, p4)
                .flatMap(p -> this.service.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);

    }
}
