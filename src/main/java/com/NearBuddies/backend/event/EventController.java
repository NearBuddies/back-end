package com.NearBuddies.backend.event;


import com.NearBuddies.backend.Utils.EventUtils;
import com.NearBuddies.backend.membership.MembershipRepository;
import com.NearBuddies.backend.registration.Status;
import com.NearBuddies.backend.registration.Type;
import com.NearBuddies.backend.user.User;
import com.NearBuddies.backend.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.NearBuddies.backend.Utils.PhotoUtils.compressPhoto;
import static com.NearBuddies.backend.Utils.PhotoUtils.decompressPhoto;

@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final MembershipRepository membershipRepository;

    public EventController(EventService eventService, UserService userService, MembershipRepository membershipRepository) {
        this.eventService = eventService;
        this.userService = userService;
        this.membershipRepository = membershipRepository;
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Event> createEvent(@RequestPart("body") String eventString,
                                             @RequestPart("photo") MultipartFile posterPhoto,
                                             @RequestPart("organizer") String organizerId) throws IOException {
        byte[] poster = compressPhoto(posterPhoto.getBytes());
        Event event = EventUtils.getEventFromString(eventString);
        event.setPoster(poster);
        User organizer = userService.findUserById(organizerId);
        Event savedEvent = this.eventService.create(event, organizer).block();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @GetMapping("/findOne/{id}")
    public Mono<ResponseEntity<Event>> findEventById(@PathVariable("id") String eventId){
        return this.eventService.findById(eventId)
                .map( event ->  {
                    event.setPoster(decompressPhoto(event.getPoster()));
                    return ResponseEntity.status(HttpStatus.OK).body(event);
                });
    }

    @PostMapping("/register/{userId}/{eventId}")
    public Mono<ResponseEntity<?>> registerForEvent(@PathVariable("userId") String userId,
                                                    @PathVariable("eventId") String eventId,
                                                    @RequestParam("type") String typeString,
                                                    @RequestParam("status") String statusString) {
        Event event = eventService.findById(eventId).block();
        User user = userService.findUserById(userId);
        Type type = Type.fromString(typeString);
        Status status = Status.fromString(statusString);
        boolean userRegistered = this.membershipRepository.existsByUserAndCommunityId(user, eventId).block();
        if(userRegistered){
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already registered for the event!"));
        }else {
            return this.eventService.register(event, user, type, status)
                    .map(updatedEvent ->
                            ResponseEntity.status(HttpStatus.OK).body(updatedEvent)
                    );
        }
    }
}
