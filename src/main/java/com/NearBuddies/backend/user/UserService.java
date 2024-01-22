package com.NearBuddies.backend.user;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> register(User user);
    User findUserById(String id);
    Mono<User> authenticate(String username, String password);
}
