package com.feliper.webfluxdemo;

import com.feliper.webfluxdemo.dto.MultiplyRequestDto;
import com.feliper.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec03PostRequestTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void postTest() {
        Mono<Response> responseMono = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildMultiplyRequest(3,5))
                .retrieve()
                .bodyToMono(Response.class);



        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getOutput() == 15)
                .verifyComplete();
    }

    private MultiplyRequestDto buildMultiplyRequest(int first, int second) {
        MultiplyRequestDto requestDto = new MultiplyRequestDto();
        requestDto.setFirst(first);
        requestDto.setSecond(second);
        return requestDto;
    }
}
