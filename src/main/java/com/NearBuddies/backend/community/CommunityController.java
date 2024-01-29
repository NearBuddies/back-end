package com.NearBuddies.backend.community;

import com.NearBuddies.backend.Utils.CommunityUtils;
import com.NearBuddies.backend.membership.Membership;
import com.NearBuddies.backend.membership.MembershipRepository;

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
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    final UserService userService;
    // Add a repository instead of service - Not to do in complex cases
    final MembershipRepository membershipRepository;


    public CommunityController(CommunityService communityService, UserService userService, MembershipRepository membershipRepository) {
        this.communityService = communityService;
        this.userService = userService;
        this.membershipRepository = membershipRepository;
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Community> createCommunity(@RequestPart("body") String communityString,
                                                     @RequestPart("photo") MultipartFile profilPhoto
                                                     ) throws IOException {
        byte[] photoInByte = compressPhoto(profilPhoto.getBytes());
        Community community = CommunityUtils.getCommunityFromString(communityString);
        community.setProfilPhoto(photoInByte);
        // User creator = UserUtils.getUserFromString(creatorString);
        Community savedCommunity = this.communityService.create(community).block();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCommunity);
    }

    @GetMapping("/findOne/{id}")
    public Mono<ResponseEntity<Community>> findCommunityById(@PathVariable("id") String communityId){
        return this.communityService.findCommunityById(communityId)
                                    .map( the_community ->  {
                                        the_community.setProfilPhoto(decompressPhoto(the_community.getProfilPhoto()));
                                        return ResponseEntity.status(HttpStatus.OK).body(the_community);
                                    });
    }

    @GetMapping("/findCommunitiesOfUser/{id}")
    public ResponseEntity<List<Community>> findCommunitiesOfUser(@PathVariable("id") String userId) {
        User user = this.userService.findUserById(userId);
        List<Membership> memberships = this.membershipRepository.findByUser(user).collectList().block();
        List<Community> communities = this.communityService.findByMembersContaining(memberships)
                .collectList()
                .block();
        for (Community community : communities) {
            community.setProfilPhoto(decompressPhoto(community.getProfilPhoto()));
        }
        return new ResponseEntity<>(communities, HttpStatus.OK);
    }

    @PostMapping("/join/{userId}/{communityId}")
    public Mono<ResponseEntity<?>> joinCommunity(@PathVariable("communityId") String communityId,
                                                 @PathVariable("userId") String userId) {
        User user = userService.findUserById(userId);
        Community community = communityService.findById(communityId);
        boolean userExists = this.membershipRepository.existsByUserAndCommunityId(user,communityId).block();
        if (userExists) {
            // User already in community
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already in the community"));
        } else {
            return this.communityService.join(community, user)
                    .map(the_community -> ResponseEntity.status(HttpStatus.OK).body(the_community));
        }
    }


}
