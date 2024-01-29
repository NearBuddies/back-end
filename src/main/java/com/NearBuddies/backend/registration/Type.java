package com.NearBuddies.backend.registration;
public enum Type {
    VIP("VIP"),
    GENERAL("General"),
    STUDENT("Student");

    private final String displayName;

    Type(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
