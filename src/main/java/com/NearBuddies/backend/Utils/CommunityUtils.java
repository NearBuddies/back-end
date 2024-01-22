package com.NearBuddies.backend.Utils;

import com.NearBuddies.backend.community.Community;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CommunityUtils {
    public static Community getCommunityFromString(String community) {
        Community communityCrafted = new Community();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            communityCrafted = objectMapper.readValue(community, Community.class);
        } catch (IOException err) {
            System.out.printf("Error", err.toString());
        }
        return communityCrafted;
    }
}
