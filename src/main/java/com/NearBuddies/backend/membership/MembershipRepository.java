package com.NearBuddies.backend.membership;

import com.NearBuddies.backend.user.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MembershipRepository extends ReactiveMongoRepository<Membership, String> {
    Flux<Membership> findByUserId(String userId);
    Mono<Boolean> existsByUserAndCommunityId(User user, String communityId);
}
