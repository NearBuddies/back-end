package com.NearBuddies.backend.community;

public enum Visibility {
    PUBLIC("Public"),
    PRIVATE("Private"),
    SECRET("Secret");

    private final String displayName;

    Visibility(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
