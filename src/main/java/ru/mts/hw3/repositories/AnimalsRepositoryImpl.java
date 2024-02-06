package ru.mts.hw3.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.services.hw5.CreateAnimalService;
import ru.mts.hw3.utils.ArrayUtils;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.*;

import static ru.mts.hw3.utils.Preconditions.checkArgument;

public class AnimalsRepositoryImpl implements AnimalsRepository {
    private Animal[] animals;
    private final ApplicationContext applicationContext;

    public AnimalsRepositoryImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        final int n = 5;

        animals = new Animal[n];

        for (int i = 0; i < n; i++) {
            CreateAnimalService createAnimalService = applicationContext.getBean(CreateAnimalService.class);
            animals[i] = createAnimalService.createAnimal();
        }
    }

    @Override
    public String[] findLeapYearNames() {
        checkDate(animals);

        String[] animalsReadyNames = Arrays.stream(animals).filter(animal -> animal.getBirthDate().isLeapYear()).map(animal -> animal.getName()).toArray(String[]::new);
        return animalsReadyNames;
    }

    @Override
    public Animal[] findOlderAnimal(int nYears) {
        checkDate(animals);
        checkArgument((nYears > 0), "n should be more then 0");

        final LocalDate currentDateMinusNYears = LocalDate.now().minusYears(nYears);

        Animal[] animalsReady = Arrays.stream(animals)
                .filter(animal -> animal.getBirthDate().isBefore(currentDateMinusNYears))
                .toArray(Animal[]::new);

        return animalsReady;
    }

    @Override
    public List<Animal> findDuplicate() {
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

        List<Animal> animalList = new ArrayList<>(resultSet);

        printDuplicates(animalList);

        return animalList;
    }

    @Override
    public void printDuplicates(List<Animal> animals) {
        for (Animal animal : animals) {
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

    public Animal[] getAnimals() {
        return animals;
    }

    public void setAnimals(Animal[] animals) {
        this.animals = animals;
    }
}
