package ru.mts.hw3.animals.animalsExt.pets;

import ru.mts.hw3.animals.AbstractAnimal;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Pet extends AbstractAnimal {
    public Pet(String name, String bread, BigDecimal cost, String character, LocalDate birthdate) {
        super(name, bread, cost, character, birthdate);
    }
}
