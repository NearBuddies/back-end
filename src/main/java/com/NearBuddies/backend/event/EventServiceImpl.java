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
            // Retrieve the organizer by ID
            return userRepository.findById(event.getOrganizer().getId()).flatMap(organizer -> {
                // Add credits to the organizer
                organizer.addCredits(event.getCredits());

                // Save the organizer, community, and registration asynchronously
                return Mono.zip(
                        userRepository.save(organizer),
                        communityRepository.save(community),
                        registrationRepository.save(new Registration(eventId, savedUser, type, status, LocalDateTime.now(), null, true))
                ).flatMap(tuple -> {
                    // Update the organizer, community, and event with saved results
                    event.setOrganizer(tuple.getT1());
                    communityRepository.save(community).subscribe();  // Subscribe to save the community asynchronously

                    // Add the registration to the event
                    event.getRegistrations().add(tuple.getT3());

                    // Save the updated event and return the result
                    return eventRepository.save(event);
                });
            });
        });
    }

    @Override
    public Mono<Event> cancel(Event event, User user) {
        return registrationRepository.findByEventIdAndAttendeeId(event.getId(), user.getId())
                .flatMap(registration -> {
                    System.out.println("Registration to be deleted: " + registration.getId());
                    // Retrieve the credits from the registration
                    int creditsToReturn = event.getCredits();

                    return registrationRepository.delete(registration)
                            .doOnSuccess(deleted -> {
                                System.out.println("Registration deleted: " + deleted);
                                event.getRegistrations().remove(registration);
                            })
                            .then(eventRepository.save(event))
                            .doOnSuccess(updatedEvent -> {
                                System.out.println("Event updated: " + updatedEvent.getId());

                                // Add credits back to the user
                                user.addCredits(creditsToReturn);
                                userRepository.save(user).subscribe();  // Subscribe to save the user asynchronously

                                // Subtract credits from the organizer
                                event.getOrganizer().subtractCredits(creditsToReturn);
                                userRepository.save(event.getOrganizer()).subscribe();  // Subscribe to save the organizer asynchronously
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
