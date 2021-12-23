package com.feliper.webfluxdemo;

import com.feliper.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.util.Map;

public class Lec07QueryParamsTest  extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void queryParamsTest() {

        Flux<Integer> integerFlux = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("jobs/search")
                        .query("count={count}&page={page}")
                        .build(Map.of(
                        "count", 10,
                        "page", 5
                )))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
