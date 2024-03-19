package ru.mts.repositories;

import ru.mts.domain.animals.Animal;
import ru.mts.utils.exceptions.InvalidAnimalsSizeException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
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
    Map<String, List<Animal>> findDuplicate();

    void printDuplicates(Collection<Animal> animals);

    public void findAverageAge(List<Animal> animals);

    /**
     * @return animals that are older than 5 years and cost more than the average cost of all
     */
    public List<Animal> findOldAndExpensive(List<Animal> animals);

    /**
     * @return 3 animals with the lowest cost
     */
    public List<Animal> findMinConstAnimal(List<Animal> animals) throws InvalidAnimalsSizeException;

}
