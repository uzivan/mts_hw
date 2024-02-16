package ru.mts.enums.animals.types;

public enum PetType {
    CAT("cat"),
    DOG("dog");

    private String title;

    PetType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
