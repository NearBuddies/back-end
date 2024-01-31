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

    public static Type fromString(String type) {
        for (Type enumType : Type.values()) {
            if (enumType.name().equalsIgnoreCase(type)) {
                return enumType;
            }
        }
        throw new IllegalArgumentException("Unknown enum constant: " + type);
    }
}
