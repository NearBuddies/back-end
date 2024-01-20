package com.NearBuddies.backend.community;

import com.NearBuddies.backend.user.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Service
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public Community findById(String id) {
        return this.communityRepository.findById(id).block();
    }

    @Override
    public Mono<Community> create(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public Community findCommunityById(String id){
        Community community = this.communityRepository.findCommunityById(id);
        return community;
    }

    @Override
    public Mono<Community> join(Community community, User user) {
        community.addMember(user);
        return communityRepository.save(community);
    }

}
