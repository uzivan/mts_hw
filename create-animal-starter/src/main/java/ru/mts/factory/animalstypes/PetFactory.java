package ru.mts.factory.animalstypes;

import ru.mts.domain.animals.animalsExt.pets.Pet;
import ru.mts.enums.animals.types.PetType;
import ru.mts.factory.Factory;

public interface PetFactory extends Factory {

    Pet createPer(PetType petType);

}
