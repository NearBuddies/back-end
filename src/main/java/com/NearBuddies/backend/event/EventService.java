package com.NearBuddies.backend.event;

import com.NearBuddies.backend.user.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
public interface EventService {
    Mono<Event> create(Event event, User organizer);
    Mono<Event> register(Event event, User user);
    Mono<Event> findById(String Id);

}
