package com.NearBuddies.backend.community;

import com.NearBuddies.backend.membership.MembershipDTO;
import com.NearBuddies.backend.user.UserDTO;
import com.NearBuddies.backend.user.model.Visibility;

import java.util.List;

public record CommunityDTO(
        String id,
        String name,
        String description,
        Visibility visibility,
        UserDTO creator,
        UserDTO admin,
        double latitude,
        double longitude,
        List<MembershipDTO> members
) {
    // You can include additional methods or annotations if needed
}