package ru.mts.domain.animals;



import ru.mts.domain.Instance;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Animal extends Instance {
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
    /**
     * @return birthdate of animal
     */
    LocalDate getBirthDate();

}
