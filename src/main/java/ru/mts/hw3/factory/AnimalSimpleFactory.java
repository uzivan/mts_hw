package ru.mts.hw3.factory;

import ru.mts.hw3.animals.Animal;
import ru.mts.hw3.animals.animalsExt.pets.petsExt.Cat;
import ru.mts.hw3.animals.animalsExt.pets.petsExt.Dog;
import ru.mts.hw3.animals.animalsExt.predators.predatorsExt.Panda;
import ru.mts.hw3.animals.animalsExt.predators.predatorsExt.Wolf;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ru.mts.hw3.enums.AnimalCharacter.HERBIVORE;
import static ru.mts.hw3.enums.AnimalCharacter.PREDATOR;

public final class AnimalSimpleFactory {

    private AnimalSimpleFactory() {
    }

    public static Animal createRandomAnimal(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("'n' must be greater than 0");
        }

        final Animal result;
        var name = String.format("name_%d", n);
        var bread = String.format("bread_%d", n);
        var cost = BigDecimal.valueOf(Math.random() * 10).setScale(2, RoundingMode.HALF_UP);
        String character;

        var rand = Math.random();
        if (rand >= 0.5) {
            if (rand <= 0.75) {
                character = PREDATOR.getDescription();
                result = new Wolf(name + "_wolf", bread, cost, character);

            } else {
                character = HERBIVORE.getDescription();
                result = new Panda(name + "_panda", bread, cost, character);
            }

        } else {
            character = PREDATOR.getDescription();
            if (rand >= 0.25) {
                result = new Cat(name + "_cat", bread, cost, character);

            } else {
                result = new Dog(name + "_dog", bread, cost, character);
            }

        }

        return result;
    }

}
