package com.NearBuddies.backend.event;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EventRepository extends ReactiveMongoRepository<Event, String> {
}
