package com.feliper.webfluxdemo.controller;

import com.feliper.webfluxdemo.dto.MultiplyRequestDto;
import com.feliper.webfluxdemo.dto.Response;
import com.feliper.webfluxdemo.service.ReactiveMathService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {

    private final ReactiveMathService mathService;

    public ReactiveMathController(ReactiveMathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return this.mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return this.mathService.multiplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return this.mathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono) {
        return mathService.multiply(requestDtoMono);
    }
}
