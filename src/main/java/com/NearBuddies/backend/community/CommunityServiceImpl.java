package com.NearBuddies.backend.community;

import com.NearBuddies.backend.membership.Membership;
import com.NearBuddies.backend.membership.MembershipRepository;
import com.NearBuddies.backend.user.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    final MembershipRepository membershipRepository;
    public CommunityServiceImpl(CommunityRepository communityRepository, MembershipRepository membershipRepository) {
        this.communityRepository = communityRepository;
        this.membershipRepository = membershipRepository;
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
    public Mono<Community> findCommunityById(String id){
        return this.communityRepository.findCommunityById(id);
    }

    @Override
    public Flux<Community> findByMembersContaining(List<Membership> memberships) {
       return this.communityRepository.findByMembersContaining(memberships);
    }

    @Override
    public Mono<Community> join(Community community, User user) {
        String id = community.getId();
        System.out.println("Id is "+id);
        community.getMembers().add(this.membershipRepository.save(new Membership(LocalDateTime.now(), id, null,true,user)).block());
        return communityRepository.save(community);
    }
}
