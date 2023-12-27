package ru.mts.hw3.factory.animalstypes;

import ru.mts.hw3.domain.animals.animalsExt.pets.Pet;
import ru.mts.hw3.enums.animals.types.PetType;
import ru.mts.hw3.factory.Factory;

public interface PetFactory extends Factory {
    public Pet createPer(PetType petType);
}
