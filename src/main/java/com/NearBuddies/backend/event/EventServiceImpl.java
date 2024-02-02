package com.NearBuddies.backend.event;

import com.NearBuddies.backend.registration.Registration;
import com.NearBuddies.backend.registration.RegistrationRepository;
import com.NearBuddies.backend.user.User;
import com.NearBuddies.backend.user.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, RegistrationRepository registrationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Mono<Event> create(Event event, User organizer) {
        event.setOrganizer(organizer);
        return eventRepository.save(event);
    }

    @Override
    public Mono<Event> register(Event event, User user) {
        event.getRegistrations().add(registrationRepository.save(new Registration(user)).block());
        return eventRepository.save(event);
    }

    @Override
    public Mono<Event> findById(String Id) {
        return this.eventRepository.findById(Id);
    }
}
