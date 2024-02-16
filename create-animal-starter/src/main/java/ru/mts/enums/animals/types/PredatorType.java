package ru.mts.enums.animals.types;

public enum PredatorType {
    WOLF("wolf"),
    PANDA("panda");

    private String title;

    PredatorType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
