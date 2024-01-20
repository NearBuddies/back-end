package com.NearBuddies.backend.community;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CommunityRepository extends ReactiveMongoRepository<Community, String> {
     Mono<Community> findCommunityById(String id);
     Mono<Community> findById(String id);
}
