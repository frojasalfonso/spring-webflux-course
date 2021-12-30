package com.feliper.orderservice.config;

import com.feliper.orderservice.dto.PurchaseOrderRequestDto;
import com.feliper.orderservice.dto.PurchaseOrderResponseDto;
import com.feliper.orderservice.service.OrderFulfillmentService;
import com.feliper.orderservice.service.OrderQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderHandler {

    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService orderQueryService;

    public OrderHandler(OrderFulfillmentService orderFulfillmentService, OrderQueryService orderQueryService) {
        this.orderFulfillmentService = orderFulfillmentService;
        this.orderQueryService = orderQueryService;
    }

    public Mono<ServerResponse> getByUserId(ServerRequest request) {
        Integer userId = Integer.valueOf(request.pathVariable("id"));
        Flux<PurchaseOrderResponseDto> purchaseOrderResponseDtoFlux = this.orderQueryService.getByUserId(userId);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(purchaseOrderResponseDtoFlux, PurchaseOrderResponseDto.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<PurchaseOrderRequestDto> requestDtoMono = request.bodyToMono(PurchaseOrderRequestDto.class);
        return this.orderFulfillmentService.processOrder(requestDtoMono)
                .flatMap(purchaseOrderResponseDto -> ServerResponse.ok()
                        .bodyValue(purchaseOrderResponseDto))
                .onErrorResume(WebClientResponseException.class, e -> ServerResponse.badRequest().build())
                .onErrorResume(WebClientRequestException.class,
                        e -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }
}
