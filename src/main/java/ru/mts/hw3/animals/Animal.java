package ru.mts.hw3.animals;

import java.math.BigDecimal;

public interface Animal {
    /**
     * @return Bread of animal
     */
    public String getBread();
    /**
     * @return Name of animal
     */
    public String getName();
    /**
     * @return Cost of animal
     */
    public BigDecimal getCost();
    /**
     * @return Character of animal
     */
    public String getCharacter();
}
