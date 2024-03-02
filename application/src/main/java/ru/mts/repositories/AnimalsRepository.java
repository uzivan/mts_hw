package ru.mts.repositories;

import ru.mts.domain.animals.Animal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public interface AnimalsRepository {

    /**
     * @return names of animals that were born in the leap year
     */
    Map<String, LocalDate> findLeapYearNames();

    /**
     * @return animals that older then nYears
     */
    Map<Animal, Integer> findOlderAnimal(int nYears);

    /**
     * @return animals that repeat
     */
    Map<String, Integer> findDuplicate();

    void printDuplicates(Collection<Animal> animals);

}
