package com.NearBuddies.backend.registration;

import com.NearBuddies.backend.user.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RegistrationRepository extends ReactiveMongoRepository<Registration, String> {
    Mono<Boolean> existsByAttendeeAndEventId(User attendee, String eventId);
}
