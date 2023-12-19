package ru.mts.hw3.animals.animalsExt.predators.predatorsExt;

import ru.mts.hw3.animals.animalsExt.predators.Predator;

import java.math.BigDecimal;

public class Wolf extends Predator {

    public Wolf(String name, String bread, BigDecimal cost, String character) {
        super(name, bread, cost, character);
    }

    @Override
    public String getBread() {
        return bread;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String getCharacter() {
        return character;
    }
}
