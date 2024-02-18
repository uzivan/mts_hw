package ru.mts.domain.animals;


import ru.mts.enums.animals.AnimalType;

public class AnimalTypeChoose<T> {

    private AnimalType animalType;
    private T heirType;

    public AnimalTypeChoose(AnimalType animalType, T heirType) {
        this.animalType = animalType;
        this.heirType = heirType;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public T getHeirType() {
        return heirType;
    }

    public void setHeirType(T heirType) {
        this.heirType = heirType;
    }

}
