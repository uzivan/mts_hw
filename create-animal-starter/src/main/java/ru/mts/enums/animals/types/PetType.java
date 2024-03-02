package ru.mts.enums.animals.types;

public enum PetType implements AnimalTypeSample{

    CAT("cat", "Cat"),
    DOG("dog", "Dog");

    private final String title;
    private final String name;

    PetType(String title, String name) {
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
