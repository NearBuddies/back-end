package com.NearBuddies.backend.registration;

import com.NearBuddies.backend.user.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RegistrationRepository extends ReactiveMongoRepository<Registration, String> {
    Mono<Boolean> existsByEventIdAndAttendeeId(String eventId, String attendeeId);

    Mono<Registration> findByEventIdAndAttendeeId(String id, String attendeeId);
}
