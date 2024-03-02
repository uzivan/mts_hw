package ru.mts.enums.animals.types;

public enum PredatorType implements AnimalTypeSample{

    WOLF("wolf", "Wolf"),
    PANDA("panda", "Panda");

    private final String title;
    private final String name;

    PredatorType(String title, String name) {
        this.title = title;
        this.name = name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getName() {
        return name;
    }

}
