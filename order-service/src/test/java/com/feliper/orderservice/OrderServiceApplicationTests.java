package com.feliper.orderservice;

import com.feliper.orderservice.client.ProductClient;
import com.feliper.orderservice.client.UserClient;
import com.feliper.orderservice.dto.PurchaseOrderRequestDto;
import com.feliper.orderservice.dto.PurchaseOrderResponseDto;
import com.feliper.orderservice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Test
    void contextLoads() {
        Flux<PurchaseOrderResponseDto> purchaseOrderResponseDtoFlux =
                Flux.zip(userClient.getAllUser(), productClient.getAllProduct())
                        .map(objects -> PurchaseOrderRequestDto.builder()
                                .userId(objects.getT1().getId())
                                .productId(objects.getT2().getId())
                                .build())
                        .flatMap(purchaseOrderRequestDto -> orderFulfillmentService.processOrder(
                                Mono.just(purchaseOrderRequestDto)))
                        .doOnNext(System.out::println);

        StepVerifier.create(purchaseOrderResponseDtoFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

}
