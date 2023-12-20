package ru.mts.hw3.animals;

import java.math.BigDecimal;

public interface Animal {

    /**
     * @return Bread of animal
     */
    String getBread();

    /**
     * @return Name of animal
     */
    String getName();

    /**
     * @return Cost of animal
     */
    BigDecimal getCost();

    /**
     * @return Character of animal
     */
    String getCharacter();

}
