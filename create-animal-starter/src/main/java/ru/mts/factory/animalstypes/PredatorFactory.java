package ru.mts.factory.animalstypes;

import ru.mts.domain.animals.animalsExt.predators.Predator;
import ru.mts.enums.animals.types.PredatorType;
import ru.mts.factory.Factory;

public interface PredatorFactory extends Factory {

    Predator createPredator(PredatorType predatorType);

}
