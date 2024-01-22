package com.NearBuddies.backend.membership;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MembershipRepository extends ReactiveMongoRepository<Membership, String> {
}
