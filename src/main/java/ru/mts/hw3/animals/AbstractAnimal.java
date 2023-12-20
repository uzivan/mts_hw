package ru.mts.hw3.animals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class AbstractAnimal implements Animal {

    protected String bread;
    protected String name;
    protected BigDecimal cost;
    protected String character;

    public AbstractAnimal(String name, String bread, BigDecimal cost, String character) {
        this.bread = bread;
        this.name = name;
        this.cost = Objects.isNull(cost) ? null : cost.setScale(2, RoundingMode.HALF_UP);
        this.character = character;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                ": {" +
                "bread='" + getBread() + '\'' +
                ", name='" + getName() + '\'' +
                ", cost=" + getCost() +
                ", character='" + getCharacter() + '\'' +
                '}';
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
