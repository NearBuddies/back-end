package com.NearBuddies.backend.community.management;

import com.NearBuddies.backend.community.CommunityDTO;
import com.NearBuddies.backend.community.CommunityExternalAPI;
import com.NearBuddies.backend.community.CommunityInternalAPI;
import com.NearBuddies.backend.community.mapper.CommunityMapper;
import com.NearBuddies.backend.community.model.Community;
import com.NearBuddies.backend.community.repository.CommunityRepository;
import reactor.core.publisher.Mono;

public class CommunityManagement implements CommunityInternalAPI, CommunityExternalAPI {
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;

    public CommunityManagement(CommunityRepository communityRepository, CommunityMapper communityMapper) {
        this.communityRepository = communityRepository;
        this.communityMapper = communityMapper;
    }

    @Override
    public Mono<CommunityDTO> create(CommunityDTO communityDTO) {
        Community community = communityMapper.communityDTOToCommunity(communityDTO);
        return communityRepository.save(community)
                .map(savedCommunity -> communityMapper.communityToCommunityDTO(savedCommunity));
    }

    @Override
    public Mono<UserDTO> join(UserDTO userDTO) {
        return null;
    }
}
