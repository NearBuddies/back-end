package com.NearBuddies.backend.community;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CommunityRepository extends ReactiveMongoRepository<Community, String> {
     Community findCommunityById(String id);
}
