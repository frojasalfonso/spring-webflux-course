package com.feliper.userservice.config;

import com.feliper.userservice.dto.UserDto;
import com.feliper.userservice.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getAllHandler(ServerRequest request) {
        Flux<UserDto> userDtoFlux = this.userService.getAll();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userDtoFlux, UserDto.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return this.userService.getUserById(id)
                .flatMap(userDto -> ServerResponse.ok().body(Mono.just(userDto), UserDto.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<UserDto> requestDtoMono = request.bodyToMono(UserDto.class);
        Mono<UserDto> userDtoMono = this.userService.createUser(requestDtoMono);
        return ServerResponse.ok()
                .body(userDtoMono, UserDto.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        Mono<UserDto> requestDtoMono = request.bodyToMono(UserDto.class);
        return this.userService.updateUser(id, requestDtoMono)
                .flatMap(userDto -> ServerResponse.ok().body(Mono.just(userDto), UserDto.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        Mono<Void> voidMono = this.userService.deleteUserById(id);
        return ServerResponse.noContent().build(voidMono);
    }

}
