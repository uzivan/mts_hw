package ru.mts.hw3.animals.animalsExt.pets.petsExt;

import ru.mts.hw3.animals.animalsExt.pets.Pet;

import java.math.BigDecimal;

public class Cat extends Pet {
    public Cat(String name, String bread, BigDecimal cost, String character) {
        super(name,bread, cost, character);
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
