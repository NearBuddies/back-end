package com.NearBuddies.backend.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.NearBuddies.backend.user.UserDTO;
import com.NearBuddies.backend.user.model.User;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
