package com.NearBuddies.backend.event;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository extends ReactiveMongoRepository<Event, String> {
    Mono<Event> findById(String id);
    Flux<Event> findByCommunityId(String communityId);
}
