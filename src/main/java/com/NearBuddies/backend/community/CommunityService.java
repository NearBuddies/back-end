package com.NearBuddies.backend.community;

import com.NearBuddies.backend.user.User;
import reactor.core.publisher.Mono;

public interface CommunityService {
    public Mono<Community> create(Community community);
    public Mono<Community> join(Community community, User user);
}
