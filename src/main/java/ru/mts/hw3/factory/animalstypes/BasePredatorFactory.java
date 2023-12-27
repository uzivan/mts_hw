package ru.mts.hw3.factory.animalstypes;

import ru.mts.hw3.domain.animals.animalsExt.predators.Predator;
import ru.mts.hw3.domain.animals.animalsExt.predators.predatorsExt.Panda;
import ru.mts.hw3.domain.animals.animalsExt.predators.predatorsExt.Wolf;
import ru.mts.hw3.enums.animals.types.PredatorType;
import ru.mts.hw3.factory.SimpleFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Random;

import static ru.mts.hw3.enums.AnimalCharacter.HERBIVORE;
import static ru.mts.hw3.enums.AnimalCharacter.PREDATOR;

public class BasePredatorFactory {
    public Predator createPredator(PredatorType predatorType){
        Random random = new Random();

        var name = String.format("name_%d", random.nextInt(100));
        var bread = "bread_predator_";
        var cost = BigDecimal.valueOf(1 + (100 - 1) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
        String character;
        LocalDate birthdate = SimpleFactory.createRandomLocalDate(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 12, 31)
        );

        Predator predator = null;
        switch (predatorType){
            case WOLF:
                character = HERBIVORE.getDescription();
                predator = new Wolf(name, bread + "wolf", cost, character, birthdate);

                break;

            case PANDA:
                character = PREDATOR.getDescription();
                predator = new Panda(name, bread + "panda", cost, character, birthdate);

                break;

            default:
                throw new UnsupportedOperationException("unsupported case");
        }
        return predator;
    }

}
