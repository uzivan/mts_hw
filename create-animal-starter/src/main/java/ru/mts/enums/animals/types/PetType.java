package ru.mts.enums.animals.types;

public enum PetType implements AnimalTypeSample{
    CAT("cat"),
    DOG("dog");

    private final String title;

    PetType(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

}
