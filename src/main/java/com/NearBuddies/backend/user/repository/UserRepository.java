package com.NearBuddies.backend.user.repository;

import com.NearBuddies.backend.user.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsernameAndPassword(String username, String password);
}
