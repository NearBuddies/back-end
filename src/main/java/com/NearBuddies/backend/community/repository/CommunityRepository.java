package com.NearBuddies.backend.community.repository;

import com.NearBuddies.backend.community.model.Community;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommunityRepository extends ReactiveMongoRepository<Community, String> {
}
