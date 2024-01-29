package com.NearBuddies.backend.event;

public enum Type {
    PUBLIC("Public"),
    PRIVATE("Private"),
    SECRET("Secret");

    private final String displayName;

    Type(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
