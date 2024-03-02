package ru.mts.factory.animalstypes;

import org.springframework.core.env.Environment;
import ru.mts.domain.animals.animalsExt.predators.Predator;
import ru.mts.enums.animals.types.PredatorType;
import ru.mts.factory.Factory;

public interface PredatorFactory extends Factory {

    public Predator createPredator(PredatorType predatorType);

}
