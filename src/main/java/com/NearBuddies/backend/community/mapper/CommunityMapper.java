package com.NearBuddies.backend.community.mapper;

import com.NearBuddies.backend.community.CommunityDTO;
import com.NearBuddies.backend.community.model.Community;
import com.NearBuddies.backend.user.UserDTO;
import com.NearBuddies.backend.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommunityMapper {
    CommunityDTO communityToCommunityDTO(Community communityDT);
    Community communityDTOToCommunity(CommunityDTO communityDTO);
}
