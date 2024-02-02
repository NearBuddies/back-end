package com.NearBuddies.backend.event;

import com.NearBuddies.backend.community.Community;
import com.NearBuddies.backend.community.CommunityRepository;
import com.NearBuddies.backend.registration.Registration;
import com.NearBuddies.backend.registration.RegistrationRepository;
import com.NearBuddies.backend.registration.Status;
import com.NearBuddies.backend.registration.Type;
import com.NearBuddies.backend.user.User;
import com.NearBuddies.backend.user.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;
    private final CommunityRepository communityRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, RegistrationRepository registrationRepository, CommunityRepository communityRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationRepository = registrationRepository;
        this.communityRepository = communityRepository;
    }

    @Override
    public Mono<Event> create(Event event, User organizer) {
        event.setOrganizer(organizer);
        return eventRepository.save(event);
    }

    @Override
    public Mono<Event> register(Event event, User user, Type type, Status status, Community community) {
        String eventId = event.getId();

        // Subtract credits from the user
        user.subtractCredits(event.getCredits());

        // Save the updated user (returns a Mono<User>)
        Mono<User> savedUserMono = userRepository.save(user);

        // Perform additional operations when the user is saved
        return savedUserMono.flatMap(savedUser -> {
            // Add credits to the organizer
            event.getOrganizer().addCredits(event.getCredits());
            userRepository.save(event.getOrganizer()).subscribe();  // Subscribe to save the organizer asynchronously

            // Add the event to the community
            community.getEvents().add(event);
            communityRepository.save(community).subscribe();  // Subscribe to save the community asynchronously

            // Create and save the registration
            Registration registration = new Registration(eventId, savedUser, type, status, LocalDateTime.now(), null, true);
            return registrationRepository.save(registration).map(savedRegistration -> {
                // Add the registration to the event
                event.getRegistrations().add(savedRegistration);

                // Save the updated event and return the result
                return eventRepository.save(event);
            });
        }).block();
    }

    @Override
    public Mono<Event> cancel(Event event, User user) {
        return registrationRepository.findByEventIdAndAttendeeId(event.getId(), user.getId())
                .flatMap(registration -> {
                    System.out.println("Registration to be deleted: " + registration.getId());
                    return registrationRepository.delete(registration)
                            .doOnSuccess(deleted -> {
                                System.out.println("Registration deleted: " + deleted);
                                event.getRegistrations().remove(registration);
                            })
                            .then(eventRepository.save(event))
                            .doOnSuccess(updatedEvent -> {
                                System.out.println("Event updated: " + updatedEvent.getId());
                            });
                });
    }
    @Override
    public Mono<Event> findById(String Id) {
        return this.eventRepository.findById(Id);
    }

    @Override
    public Flux<Event> eventsByCommunity(String communityId) {
        return eventRepository.findByCommunityId(communityId);
    }
}
