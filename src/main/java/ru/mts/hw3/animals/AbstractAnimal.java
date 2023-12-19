package ru.mts.hw3.animals;

import java.math.BigDecimal;

public abstract class AbstractAnimal implements Animal {
    public AbstractAnimal(String name, String bread, BigDecimal cost, String character) {
        this.bread = bread;
        this.name = name;
        this.cost = cost;
        this.character = character;
    }

    protected String bread;
    protected String name;
    protected BigDecimal cost;

    protected String character;

    @Override
    public String toString() {
        return "AbstractAnimal{" +
                "bread='" + bread + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", character='" + character + '\'' +
                '}';
    }
}
