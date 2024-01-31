package com.NearBuddies.backend.event;

import com.NearBuddies.backend.registration.Status;
import com.NearBuddies.backend.registration.Type;
import com.NearBuddies.backend.user.User;
import reactor.core.publisher.Mono;

public interface EventService {
    public Mono<Event> create(Event event, User organizer);
    public Mono<Event> register(Event event, User user, Type type, Status status);
    public Mono<Event> findById(String Id);

}
