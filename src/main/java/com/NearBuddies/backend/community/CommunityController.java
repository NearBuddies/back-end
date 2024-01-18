package com.NearBuddies.backend.community;

import com.NearBuddies.backend.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping("/new")
    public Mono<ResponseEntity<Community>> createCommunity(@RequestBody Community community) {
        return communityService.create(community)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser));
    }

    @PostMapping("/join")
    public Mono<ResponseEntity<Community>> joinCommunity(@RequestBody Community community, User user) {
        return communityService.join(community, user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.OK).body(savedUser));
    }

}
