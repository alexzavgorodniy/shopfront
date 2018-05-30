package model;

import java.util.Arrays;

public enum Availability {
    ABSENT("ABSENT"),
    PRESENT("PRESENT");

    private String name;

    Availability() {
    }

    private Availability(final String name) {
        this.name = name;
    }

    public static Availability of(String availability) {
        return Arrays.stream(Availability.values())
                .filter(a -> a.name().equalsIgnoreCase(availability))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find availability by name: " + availability));
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
