package ru.mts.domain.animals.animalsExt.pets.petsExt;

import ru.mts.domain.animals.animalsExt.pets.Pet;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Dog extends Pet {

    public Dog(String name, String bread, BigDecimal cost, String character, LocalDate birthDate) {
        super(name, bread, cost, character, birthDate);
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

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

}
