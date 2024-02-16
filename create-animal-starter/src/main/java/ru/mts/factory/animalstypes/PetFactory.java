package ru.mts.factory.animalstypes;

import org.springframework.core.env.Environment;
import ru.mts.domain.animals.animalsExt.pets.Pet;
import ru.mts.enums.animals.types.PetType;
import ru.mts.factory.Factory;

public interface PetFactory extends Factory {
    public Pet createPer(PetType petType);
}
