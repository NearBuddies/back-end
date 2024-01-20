package com.NearBuddies.backend.community;

import com.NearBuddies.backend.Utils.CommunityUtils;
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

@Controller
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;
    final UserService userService;

    public CommunityController(CommunityService communityService, UserService userService) {
        this.communityService = communityService;
        this.userService = userService;
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Community> createCommunity(@RequestPart("body") String communityString,
                                                     @RequestPart("photo") MultipartFile profilPhoto) throws IOException {
        byte[] photoInByte = compressPhoto(profilPhoto.getBytes());
        Community community = CommunityUtils.getCommunityFromString(communityString);
        community.setProfilPhoto(photoInByte);
        Community savedCommunity = this.communityService.create(community).block();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCommunity);
    }

    @GetMapping("/findOne/{id}")
    public Mono<ResponseEntity<Community>> findCommunityById(@PathVariable("id") String communityId){
        return this.communityService.findCommunityById(communityId)
                                    .map( the_community ->  ResponseEntity.status(HttpStatus.OK).body(the_community));
    }

    @PostMapping("/join/{userId}/{communityId}")
    public Mono<ResponseEntity<?>> joinCommunity(@PathVariable("communityId") String communityId,
                                                 @PathVariable("userId") String userId) {
        Community community = communityService.findCommunityById(communityId).block();
        User user = userService.findUserById(userId);
        return this.communityService.join(community, user)
                .map( the_community ->
                     ResponseEntity.status(HttpStatus.OK).body(the_community)
                );
    }

}
