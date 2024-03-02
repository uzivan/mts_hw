package ru.mts.factory.animalstypes;

import ru.mts.domain.animals.animalsExt.predators.Predator;
import ru.mts.domain.animals.animalsExt.predators.predatorsExt.Panda;
import ru.mts.domain.animals.animalsExt.predators.predatorsExt.Wolf;
import ru.mts.enums.animals.types.PredatorType;
import ru.mts.factory.SimpleFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static ru.mts.enums.AnimalCharacter.HERBIVORE;
import static ru.mts.enums.AnimalCharacter.PREDATOR;

public class BasePredatorFactory {

    private final Map<PredatorType, List<String>> names;

    public BasePredatorFactory(Map<PredatorType, List<String>> names) {
        this.names = names;
    }

    public Predator createPredator(PredatorType predatorType){
        Random random = new Random();

        var name = SimpleFactory.chooseNameFromArray(names.get(predatorType));
        var bread = "bread_predator_";
        var cost = BigDecimal.valueOf(1 + (100 - 1) * random.nextDouble()).setScale(2, RoundingMode.HALF_UP);
        String character;

        LocalDate birthdate = SimpleFactory.createRandomLocalDate(
                LocalDate.of(2019, 1, 1),
                LocalDate.of(2024, 12, 31)
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
