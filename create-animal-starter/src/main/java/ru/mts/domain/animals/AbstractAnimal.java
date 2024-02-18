package ru.mts.domain.animals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractAnimal implements Animal {

    protected String bread;
    protected String name;
    protected BigDecimal cost;
    protected String character;
    protected LocalDate birthDate;

    public AbstractAnimal(String name, String bread, BigDecimal cost, String character, LocalDate birthDate) {
        this.bread = bread;
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "bread='" + getBread() + '\'' +
                ", name='" + getName() + '\'' +
                ", cost=" + (Objects.isNull(getCost()) ? "null" : getCost().toPlainString()) +
                ", character='" + getCharacter() + '\'' +
                ", birthDate=" + (Objects.isNull(getBirthDate()) ? "null" : String.format("%1$td-%1$tm-%1$tY", getBirthDate())) +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(bread, name, cost, character);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractAnimal that = (AbstractAnimal) o;

        return Objects.equals(bread, that.bread)
                && Objects.equals(name, that.name)
                && Objects.equals(character, that.character)
                && Objects.equals(birthDate, that.birthDate);
    }

}
