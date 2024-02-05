package ru.mts.hw3.services.hw4.search;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.utils.ArrayUtils;

import java.time.LocalDate;
import java.util.*;

import static ru.mts.hw3.utils.Preconditions.checkArgument;

public class SearchServiceImpl implements SearchService{

    @Override
    public String[] findLeapYearNames(Animal[] animals) {
        checkDate(animals);

        String[] animalsReadyNames = Arrays.stream(animals).filter(animal -> animal.getBirthDate().isLeapYear()).map(animal -> animal.getName()).toArray(String[]::new);
        return animalsReadyNames;
    }

    @Override
    public Animal[] findOlderAnimal(Animal[] animals, int nYears) {
        checkDate(animals);
        checkArgument((nYears > 0), "n should be more then 0");

        final LocalDate currentDateMinusNYears = LocalDate.now().minusYears(nYears);

        Animal[] animalsReady = Arrays.stream(animals)
                .filter(animal -> animal.getBirthDate().isBefore(currentDateMinusNYears))
                .toArray(Animal[]::new);

        return  animalsReady;
    }

    @Override
    public List<Animal> findDuplicate(Animal[] animals) {
        checkArgument(ArrayUtils.isNotEmpty(animals), "animals[] must not be null");

        Set<Animal> resultSet = new LinkedHashSet<>();
        Set<Animal> temp = new LinkedHashSet<>();
        for (var a : animals) {
            if (temp.contains(a)) {
                resultSet.add(a);
                //System.out.println(a);
            } else {
                temp.add(a);
            }
        }

        return new ArrayList<>(resultSet);
    }

    public void printDuplicates(List<Animal> animals) {
        for(Animal animal: animals){
            System.out.println(animal);
        }
    }

    public void checkDate(Animal[] animals) {
        checkArgument(ArrayUtils.isNotEmpty(animals), "animals[] must not be empty");

        if (Arrays.stream(animals)
                .filter(Objects::nonNull)
                .anyMatch(animal -> Objects.isNull(animal.getBirthDate()))) {

            throw new IllegalArgumentException("birthdate id animals must not be null");
        }

    }
}
