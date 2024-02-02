package com.NearBuddies.backend.user;

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

    @Override
    public Mono<User> addCredits(User user, int credits) {
        user.addCredits(credits);
        return userRepository.save(user);
    }

    @Override
    public User findUserById(String id){
        User user = userRepository.findUserById(id).block();
        return user;
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }
}
