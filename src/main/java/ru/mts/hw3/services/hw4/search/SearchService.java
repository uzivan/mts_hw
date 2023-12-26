package ru.mts.hw3.services.hw4.search;

import ru.mts.hw3.animals.Animal;

public interface SearchService{
    /**
     * @return names of animals that were born in the leap year
     */
    String[] findLeapYearNames(Animal[] animals);
    /**
     * @return animals that older then nYears
     */
    Animal[] findOlderAnimal(Animal[] animals, int nYears);
    /**
     * @return animals that repeat
     */
    void findDuplicate(Animal[] animals);
}
