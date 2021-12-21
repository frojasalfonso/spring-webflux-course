package com.feliper.webfluxdemo.config;

import com.feliper.webfluxdemo.dto.MultiplyRequestDto;
import com.feliper.webfluxdemo.dto.Response;
import com.feliper.webfluxdemo.dto.exception.InputValidationException;
import com.feliper.webfluxdemo.service.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

    private final ReactiveMathService mathService;

    public RequestHandler(ReactiveMathService mathService) {
        this.mathService = mathService;
    }

    public Mono<ServerResponse> squareHandler(ServerRequest request) {
        Integer input = Integer.valueOf(request.pathVariable("input"));
        Mono<Response> responseMono = this.mathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        Integer input = Integer.valueOf(request.pathVariable("input"));
        Flux<Response> responseFlux = this.mathService.multiplicationTable(input);
        return ServerResponse.ok().body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
        Integer input = Integer.valueOf(request.pathVariable("input"));
        Flux<Response> responseFlux = this.mathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM).body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
        Mono<MultiplyRequestDto> requestDtoMono = request.bodyToMono(MultiplyRequestDto.class);
        Mono<Response> responseMono = this.mathService.multiply(requestDtoMono);
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareValidationHandler(ServerRequest request) {
        Integer input = Integer.valueOf(request.pathVariable("input"));
        if (input < 10 || input > 10) {
            return Mono.error(new InputValidationException(input));
        }
        Mono<Response> responseMono = this.mathService.findSquare(input);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
}
