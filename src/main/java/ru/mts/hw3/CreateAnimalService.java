package ru.mts.hw3;

import ru.mts.hw3.animals.animalsExt.pets.petsExt.Cat;
import ru.mts.hw3.animals.animalsExt.pets.petsExt.Dog;
import ru.mts.hw3.animals.animalsExt.predators.predatorsExt.Panda;
import ru.mts.hw3.animals.animalsExt.predators.predatorsExt.Wolf;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface CreateAnimalService {
    public default void createAnimals() {
        String[] characters = {"can bite", "can't bite"};
        String name;
        String bread;
        BigDecimal cost;
        String character;
        int n = 0;
        while (n != 10) {
            double rand = Math.random();
            name = "name_" + Integer.toString(n);
            bread = "bread_" + Integer.toString(n);
            System.out.println(name);
            cost = new BigDecimal(Math.random() * 10).setScale(2, RoundingMode.HALF_UP);
            if (rand >= 0.5) {
                if (rand <= 0.75) {
                    character = characters[0];
                    Wolf wolf = new Wolf(name + "_wolf", bread, cost, character);
                    System.out.println(wolf);
                } else {
                    character = characters[1];
                    Panda panda = new Panda(name + "_panda", bread, cost, character);
                    System.out.println(panda);
                }
            } else {
                if (rand >= 0.25) {
                    character = characters[1];
                    Cat cat = new Cat(name + "_cat", bread, cost, character);
                    System.out.println(cat);
                } else {
                    character = characters[0];
                    Dog dog = new Dog(name + "_dog", bread, cost, character);
                    System.out.println(dog);
                }
                n++;
            }
        }
    }
}
