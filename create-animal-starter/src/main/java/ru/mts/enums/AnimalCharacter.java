package ru.mts.enums;

import java.util.Optional;

public enum AnimalCharacter {

    HERBIVORE("can bite"),
    PREDATOR("can't bite");

    private final String description;

    AnimalCharacter(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<AnimalCharacter> fromDescription(String description) {
        for (AnimalCharacter a : AnimalCharacter.values()) {
            if (a.getDescription().equals(description)) {
                return Optional.of(a);
            }

        }

        return Optional.empty();
    }

}