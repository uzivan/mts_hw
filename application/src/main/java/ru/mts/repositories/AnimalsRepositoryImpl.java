package ru.mts.repositories;


import org.springframework.beans.factory.ObjectProvider;
import ru.mts.domain.animals.Animal;
import ru.mts.services.CreateAnimalService;
import ru.mts.utils.Preconditions;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AnimalsRepositoryImpl implements AnimalsRepository {

    private final ObjectProvider<CreateAnimalService> createAnimalServiceObjectProvider;

    private Map<String, List<Animal>> animals;

    public AnimalsRepositoryImpl(ObjectProvider<CreateAnimalService> createAnimalServiceObjectProvider) {
        this.createAnimalServiceObjectProvider = createAnimalServiceObjectProvider;
    }

    public void init() {
        final int n = (Integer.MAX_VALUE / 100_000_000);

        CreateAnimalService service = createAnimalServiceObjectProvider.getIfAvailable();
        if (Objects.isNull(service)) {
            throw new RuntimeException("Caramba!");
        }

        animals = service.createAnimals(n);
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        checkDateAndName(animals);

        Map<String, LocalDate> resultAnimals = new IdentityHashMap<>();

        for (Map.Entry<String, List<Animal>> animalType : animals.entrySet()) {
            List<Animal> animalsList = animalType.getValue();

            for (Animal animal : animalsList) {
                if (animal.getBirthDate().isLeapYear()) {
                    String resultAnimalKey = new String(animalType.getKey() + " " + animal.getName());

                    resultAnimals.put(resultAnimalKey, animal.getBirthDate());
                }
            }
        }

        return resultAnimals;
    }

    @Override
    public Map<Animal, Integer> findOlderAnimal(int nYears) {
        Preconditions.checkArgument(nYears > 0, "nYears most be more 0");
        checkDateAndName(animals);

        final LocalDate currentDateMinusNYears = LocalDate.now().minusYears(nYears);
        final LocalDate currentDate = LocalDate.now();

        Animal oldestAnimal = null;

        Map<Animal, Integer> resultAnimal = new HashMap<>();

        for (Map.Entry<String, List<Animal>> animalType : animals.entrySet()) {
            List<Animal> animalsList = animalType.getValue();

            for (Animal animal : animalsList) {
                if (animal.getBirthDate().isBefore(currentDateMinusNYears)) {
                    resultAnimal.put(animal, (int) animal.getBirthDate().until(currentDate, ChronoUnit.YEARS));
                }

                if (Objects.isNull(oldestAnimal)) {
                    oldestAnimal = animal;
                } else if (oldestAnimal.getBirthDate().isAfter(animal.getBirthDate())) {
                    oldestAnimal = animal;
                }
            }
        }

        if (resultAnimal.isEmpty()) {
            resultAnimal.put(oldestAnimal, (int) oldestAnimal.getBirthDate().until(currentDate, ChronoUnit.YEARS));
        }

        return resultAnimal;
    }

    @Override
    public Map<String, Integer> findDuplicate() {
        Preconditions.checkArgument(!Objects.isNull(animals), "Map of animals must not be empty");
        Preconditions.checkArgument(!animals.isEmpty(), "Map of animals must not be empty");

        Map<String, Integer> resultAnimals = new HashMap<>();
        int countDuplicates;

        for (Map.Entry<String, List<Animal>> animalMapElement : animals.entrySet()) {
            List<Animal> animalList = animalMapElement.getValue();

            countDuplicates = 0;

            Set<Animal> resultSet = new LinkedHashSet<>();

            for (var a : animalList) {
                if(resultSet.contains(a)) {
                    countDuplicates++;
                } else {
                    resultSet.add(a);
                }
            }

            if(countDuplicates>0) {
                resultAnimals.put(animalMapElement.getKey(), countDuplicates);
            }
        }

        return resultAnimals;
    }

    @Override
    public void printDuplicates(Collection<Animal> animals) {
        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }

    public void checkDateAndName(Map<String, List<Animal>> animals) {
        Preconditions.checkArgument(!Objects.isNull(animals), "Map of animals must not be null");
        Preconditions.checkArgument(!animals.isEmpty(), "Map of animals must not be empty");

        for(Map.Entry<String, List<Animal>> animalMapElement: animals.entrySet()) {
            for(Animal animal: animalMapElement.getValue()) {
                Preconditions.checkArgument(!Objects.isNull(animal.getBirthDate()), "birthdate id animals must not be null");
                Preconditions.checkArgument(!Objects.isNull(animal.getName()), "name id animals must not be null");
            }
        }
    }

}
