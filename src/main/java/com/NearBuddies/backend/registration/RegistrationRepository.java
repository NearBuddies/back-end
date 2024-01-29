package com.NearBuddies.backend.registration;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RegistrationRepository extends ReactiveMongoRepository<Registration, String> {
}
