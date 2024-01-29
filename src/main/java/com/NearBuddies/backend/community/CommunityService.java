package com.NearBuddies.backend.community;

import com.NearBuddies.backend.user.User;
import reactor.core.publisher.Mono;

public interface CommunityService {
    public Community findById(String id);
    public Mono<Community> create(Community community, User creator);
    public Mono<Community> join(Community community, User user);
    public Mono<Community> findCommunityById(String communityId);
    public Mono<Community> addAdmin(Community community, User user);
}
