package ru.mts.domain.animals.animalsExt.predators;

import ru.mts.domain.animals.AbstractAnimal;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Predator extends AbstractAnimal {

    public Predator(String name, String bread, BigDecimal cost, String character, LocalDate birthdate) {
        super(name,bread, cost, character, birthdate);
    }

}
