package ru.mts.hw3.animals.animalsExt.predators;

import ru.mts.hw3.animals.AbstractAnimal;

import java.math.BigDecimal;

public abstract class Predator extends AbstractAnimal {

    public Predator(String name, String bread, BigDecimal cost, String character) {
        super(name, bread, cost, character);
    }

}
