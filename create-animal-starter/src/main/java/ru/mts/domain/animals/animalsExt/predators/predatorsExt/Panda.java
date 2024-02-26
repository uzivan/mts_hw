package ru.mts.domain.animals.animalsExt.predators.predatorsExt;



import ru.mts.domain.animals.AbstractAnimal;
import ru.mts.domain.animals.animalsExt.predators.Predator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Panda extends Predator {
    public Panda(String name, String bread, BigDecimal cost, String character, LocalDate birthDate) {
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
