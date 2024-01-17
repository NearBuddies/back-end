package com.NearBuddies.backend.user;

public record UserDTO(
        String id,
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        double latitude,
        double longitude,
        int credits) {
}
