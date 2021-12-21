package com.feliper.webfluxdemo.config;

import com.feliper.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.util.function.BiFunction;

@Service
public class CalculatorHandler {

    public Mono<ServerResponse> additionHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }

    public Mono<ServerResponse> substractionHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a - b));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest request) {
        return process(request, (a, b) -> b != 0 ? ServerResponse.ok().bodyValue(a / b) :
                ServerResponse.badRequest().bodyValue("b can't be zero"));
    }

    public Mono<ServerResponse> process(ServerRequest request, BiFunction<Integer, Integer, Mono<ServerResponse>> function) {
        var a = Integer.valueOf(request.pathVariable("a"));
        var b = Integer.valueOf(request.pathVariable("b"));
        return function.apply(a,b);
    }
}
