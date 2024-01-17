package com.NearBuddies.backend.gateway;

import com.NearBuddies.backend.user.UserExternalAPI;
import com.NearBuddies.backend.user.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class GatewayManagement {


        private final UserExternalAPI userExternalAPI;

        public GatewayManagement(UserExternalAPI userExternalAPI) {
            this.userExternalAPI = userExternalAPI;
        }

        @PostMapping("/auth/register")
        public Mono<ResponseEntity<UserDTO>> registerUser(@RequestBody UserDTO userDTO) {
            return userExternalAPI.register(userDTO)
                    .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser));
        }

        @PostMapping("/auth/signin")
        public Mono<ResponseEntity<UserDTO>> authenticateUser(@RequestBody UserDTO userDTO) {
            return userExternalAPI.authenticate(userDTO)
                    .map(authenticatedUser -> ResponseEntity.ok().body(authenticatedUser))
                    .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }