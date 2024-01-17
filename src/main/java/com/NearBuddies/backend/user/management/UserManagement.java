package com.NearBuddies.backend.user.management;

import com.NearBuddies.backend.user.UserDTO;
import com.NearBuddies.backend.user.UserExternalAPI;
import com.NearBuddies.backend.user.UserInternalAPI;
import com.NearBuddies.backend.user.mapper.UserMapper;
import com.NearBuddies.backend.user.model.User;
import com.NearBuddies.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserManagement implements UserInternalAPI, UserExternalAPI {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserManagement(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Override
    public Mono<UserDTO> register(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        return userRepository.save(user)
                .map(savedUser -> userMapper.userToUserDTO(savedUser));
    }

    @Override
    public Mono<UserDTO> authenticate(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())
                .map(returnedUser -> userMapper.userToUserDTO(returnedUser));
    }
}
