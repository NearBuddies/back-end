package com.NearBuddies.backend.User;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> register(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
