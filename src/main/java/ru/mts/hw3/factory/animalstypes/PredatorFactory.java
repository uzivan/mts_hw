package ru.mts.hw3.factory.animalstypes;

import ru.mts.hw3.domain.animals.animalsExt.predators.Predator;
import ru.mts.hw3.enums.animals.types.PredatorType;
import ru.mts.hw3.factory.Factory;

public interface PredatorFactory extends Factory {
    public Predator createPredator(PredatorType predatorType);
}
