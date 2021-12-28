package com.feliper.userservice.service;

import com.feliper.userservice.dto.UserDto;
import com.feliper.userservice.repository.UserRepository;
import com.feliper.userservice.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<UserDto> getAll() {
        return this.userRepository.findAll().map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> getUserById(Integer id) {
        return this.userRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
        return userDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> updateUser(Integer id, Mono<UserDto> userDtoMono) {
        return this.userRepository.findById(id)
                .flatMap(v -> userDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(user -> user.setId(id)))
                .flatMap(this.userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUserById(Integer id) {
        return this.userRepository.deleteById(id);
    }
}
