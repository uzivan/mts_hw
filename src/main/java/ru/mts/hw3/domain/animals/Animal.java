package ru.mts.hw3.domain.animals;

import ru.mts.hw3.domain.Instance;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Animal extends Instance {
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
    /**
     * @return birthdate of animal
     */
    public LocalDate getBirthDate();

    boolean equals(AbstractAnimal o);
}
