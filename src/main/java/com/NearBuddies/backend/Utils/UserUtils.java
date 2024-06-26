package com.NearBuddies.backend.Utils;


import com.NearBuddies.backend.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class UserUtils {
    public static User getUserFromString(String userString) {
        User user = new User();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            user = objectMapper.readValue(userString, User.class);
        } catch (IOException err) {
            System.out.printf("Error", err.toString());
        }
        return user;
    }
}