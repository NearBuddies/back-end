package com.NearBuddies.backend.event;


import com.NearBuddies.backend.Utils.EventUtils;
import com.NearBuddies.backend.Utils.UserUtils;
import com.NearBuddies.backend.user.User;
import com.NearBuddies.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.NearBuddies.backend.Utils.PhotoUtils.compressPhoto;

@Controller
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    public ResponseEntity<Event> createEvent(@RequestPart("body") String eventString,
                                             @RequestPart("photo") MultipartFile posterPhoto,
                                             @RequestPart("user") String organizerString) throws IOException {
        byte[] poster = compressPhoto(posterPhoto.getBytes());
        Event event = EventUtils.getEventFromString(eventString);
        event.setPoster(poster);
        User organizer = UserUtils.getUserFromString(organizerString);
        Event savedEvent = this.eventService.create(event, organizer).block();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @PostMapping("/join/{userId}/{eventId}")
    public Mono<ResponseEntity<?>> registerForEvent(@PathVariable("userId") String userId,
                                                    @PathVariable("eventId") String eventId) {
        Event event = eventService.findById(eventId).block();
        User user = userService.findUserById(userId);
        return this.eventService.register(event, user)
                .map( updatedEvent ->
                        ResponseEntity.status(HttpStatus.OK).body(updatedEvent)
                );
    }
}
