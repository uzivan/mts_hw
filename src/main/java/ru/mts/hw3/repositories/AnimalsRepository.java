package ru.mts.hw3.repositories;

import ru.mts.hw3.domain.animals.Animal;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface AnimalsRepository {

    /**
     * @return names of animals that were born in the leap year
     */
    String[] findLeapYearNames();

    /**
     * @return animals that older then nYears
     */
    Animal[] findOlderAnimal(int nYears);

    /**
     * @return animals that repeat
     */
    Set<Animal> findDuplicate();

    void printDuplicates(Collection<Animal> animals);
}
