package com.NearBuddies.backend.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsernameAndPassword(String username, String password);
    Mono<User> findUserById(String id);
}
