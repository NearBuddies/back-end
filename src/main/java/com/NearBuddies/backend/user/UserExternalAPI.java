package com.NearBuddies.backend.user;

import reactor.core.publisher.Mono;

public interface UserExternalAPI {

    Mono<UserDTO> register(UserDTO userDTO);
    Mono<UserDTO> authenticate(UserDTO userDTO);
}