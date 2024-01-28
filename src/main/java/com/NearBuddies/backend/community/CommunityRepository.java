package com.NearBuddies.backend.community;

import com.NearBuddies.backend.membership.Membership;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CommunityRepository extends ReactiveMongoRepository<Community, String> {
     Mono<Community> findCommunityById(String id);
     Mono<Community> findById(String id);

     Mono<Community> findByMembersContaining(List<Membership> memberships);
}
