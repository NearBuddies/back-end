package com.NearBuddies.backend.community;

import com.NearBuddies.backend.membership.Membership;
import com.NearBuddies.backend.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CommunityService {
    Community findById(String id);
    Mono<Community> create(Community community);
    Mono<Community> join(Community community, User user);

    Mono<Community> findCommunityById(String communityId);

    // List<Community> findByMembersContaining(List<Membership> list);
    Flux<Community> findByMembersContaining(List<Membership> memberships);

}
