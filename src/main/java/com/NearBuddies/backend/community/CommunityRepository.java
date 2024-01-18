package com.NearBuddies.backend.community;

import com.NearBuddies.backend.community.Community;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommunityRepository extends ReactiveMongoRepository<Community, String> {
}
