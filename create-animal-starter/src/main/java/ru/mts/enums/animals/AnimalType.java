package ru.mts.enums.animals;

import ru.mts.enums.animals.types.AnimalTypeSample;
import ru.mts.enums.animals.types.PetType;
import ru.mts.enums.animals.types.PredatorType;

public enum AnimalType {

    PET(PetType.values()),
    PREDATOR(PredatorType.values());

    private final AnimalTypeSample[] animalTypeSamples;

    AnimalType(AnimalTypeSample[] animalTypeSamples) {
        this.animalTypeSamples = animalTypeSamples;
    }

    public AnimalTypeSample[] getAnimalTypeSamples() {
        return animalTypeSamples;
    }

}
