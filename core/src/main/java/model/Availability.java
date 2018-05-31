package model;

import java.util.Arrays;

public enum Availability {
    ABSENT,
    PRESENT;

    Availability() {
    }

    public static Availability of(String availability) {
        return Arrays.stream(Availability.values())
                .filter(a -> a.name().equalsIgnoreCase(availability))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find availability by name: " + availability));
    }
}
