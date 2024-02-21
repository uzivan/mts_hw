package ru.mts.enums.animals.types;

public enum PredatorType implements AnimalTypeSample{
    WOLF("wolf"),
    PANDA("panda");

    private String title;

    PredatorType(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
