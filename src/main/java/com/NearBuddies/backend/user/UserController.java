package com.NearBuddies.backend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<User>> registerUser(@RequestBody User user) {
        return userService.register(user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser));
    }

    @PostMapping("/signin")
    public Mono<ResponseEntity<User>> authenticateUser(@RequestBody User user) {
        return userService.authenticate(user.getUsername(), user.getPassword())
                .map(authenticatedUser -> ResponseEntity.ok().body(authenticatedUser))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/credits/add/{userId}")
    public Mono<ResponseEntity<User>> registerForEvent(@PathVariable("userId") String userId,
                                                    @RequestParam("credits") int credits){
        User user = userService.findUserById(userId);
        return userService.addCredits(user, credits) .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser));
    }
}