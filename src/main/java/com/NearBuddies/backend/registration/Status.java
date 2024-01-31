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
    public static Status fromString(String status) {
        for (Status enumStatus : Status.values()) {
            if (enumStatus.name().equalsIgnoreCase(status)) {
                return enumStatus;
            }
        }
        throw new IllegalArgumentException("Unknown enum constant: " + status);
    }
}
