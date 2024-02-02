package com.NearBuddies.backend.event;


import com.NearBuddies.backend.Utils.EventUtils;
import com.NearBuddies.backend.community.Community;
import com.NearBuddies.backend.community.CommunityRepository;
import com.NearBuddies.backend.registration.RegistrationRepository;
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
import java.util.List;

import static com.NearBuddies.backend.Utils.PhotoUtils.compressPhoto;
import static com.NearBuddies.backend.Utils.PhotoUtils.decompressPhoto;

@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final RegistrationRepository registrationRepository;
    private final CommunityRepository communityRepository;

    public EventController(EventService eventService, UserService userService, RegistrationRepository registrationRepository, CommunityRepository communityRepository) {
        this.eventService = eventService;
        this.userService = userService;
        this.registrationRepository = registrationRepository;
        this.communityRepository = communityRepository;
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

    @GetMapping("/communityEvents/{id}")
    public ResponseEntity<List<Event>> findEventByCommunity(@PathVariable("id") String communityId){
        return new ResponseEntity<>(eventService.eventsByCommunity(communityId).collectList().block(), HttpStatus.OK);
    }


    @PostMapping("/register/{userId}/{eventId}/{type}/{status}")
    public Mono<ResponseEntity<?>> registerForEvent(@PathVariable("userId") String userId,
                                                    @PathVariable("eventId") String eventId,
                                                    @PathVariable("type") String typeString,
                                                    @PathVariable("status") String statusString) {
        Event event = eventService.findById(eventId).block();
        User user = userService.findUserById(userId);
        Type type = Type.fromString(typeString);
        Status status = Status.fromString(statusString);
        Community community = communityRepository.findById(event.getCommunityId()).block();
        boolean userRegistered = registrationRepository.existsByEventIdAndAttendeeId(eventId, user.getId()).block();
        if(userRegistered){
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already registered for the event!"));
        }else {
            if(user.getCredits()<event.getCredits()){
                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User don't have enough credits to register!"));
            }else{

                return this.eventService.register(event, user, type, status, community)
                        .map(updatedEvent ->
                                ResponseEntity.status(HttpStatus.OK).body(updatedEvent)
                        );
            }
        }
    }

    @PostMapping("/cancel/{userId}/{eventId}")
    public Mono<ResponseEntity<?>> cancelRegistration(@PathVariable("userId") String userId,
                                                    @PathVariable("eventId") String eventId){
        Event event = eventService.findById(eventId).block();
        User user = userService.findUserById(userId);
        boolean userRegistered = registrationRepository.existsByEventIdAndAttendeeId(eventId, user.getId()).block();
        if(userRegistered){
            return this.eventService.cancel(event, user)
                    .map(updatedEvent ->
                            ResponseEntity.status(HttpStatus.OK).body(updatedEvent)
                    );
        }else{

            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not registered for event!"));
        }
    }
}
