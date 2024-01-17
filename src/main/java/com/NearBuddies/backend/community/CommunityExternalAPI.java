package com.NearBuddies.backend.community;

import com.NearBuddies.backend.community.model.Community;
import com.NearBuddies.backend.user.UserDTO;
import reactor.core.publisher.Mono;

public interface CommunityExternalAPI {
    Mono<CommunityDTO> create(CommunityDTO communityDTO);
    Mono<UserDTO> join(UserDTO userDTO);
}
