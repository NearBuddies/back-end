package com.NearBuddies.backend.registration;
public enum Status {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    WAITLISTED("Waitlisted"),
    CANCELLED("Cancelled");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
