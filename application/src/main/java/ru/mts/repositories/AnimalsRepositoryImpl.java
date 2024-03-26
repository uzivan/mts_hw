package ru.mts.repositories;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.ObjectProvider;
import ru.mts.domain.animals.Animal;
import ru.mts.factory.SimpleFactoryFunctions;
import ru.mts.services.CreateAnimalService;
import ru.mts.utils.Preconditions;
import ru.mts.utils.exceptions.InvalidAnimalsSizeException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        SimpleFactoryFunctions.createRandomAnimals(animals, 2);
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        checkDateAndName(animals);

        return animals.entrySet()
                .stream()
                .flatMap(entry -> entry.getValue()
                        .stream()
                        .map(a -> new AbstractMap.SimpleEntry<>(entry.getKey() + " " + a.getName(), a.getBirthDate())))
                .filter(a -> a.getValue().isLeapYear())
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        (existingValue, newValue) -> existingValue,
                        ConcurrentHashMap::new)
                );
    }

    @Override
    public Map<Animal, Integer> findOlderAnimal(int nYears) {
        Preconditions.checkYears(nYears > 0);
        Preconditions.checkArgument(MapUtils.isNotEmpty(animals), "Map animal is null or empty");

        final var now = LocalDate.now();

        return animals.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .peek(a -> {
                    Preconditions.checkAnimalField(Objects.nonNull(a.getBirthDate()), "Birthdate");
                    Preconditions.checkAnimalField(Objects.nonNull(a.getName()), "Name");
                })
                .sorted(Comparator.comparing(Animal::getBirthDate))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            Optional<Animal> firstAnimal = list.stream().findFirst();

                            if (firstAnimal.isPresent()) {
                                if (firstAnimal.get().getBirthDate().isBefore(now.minusYears(nYears))) {
                                    return list.stream()
                                            .filter(a -> a.getBirthDate().isBefore(now.minusYears(nYears)))
                                            .collect(Collectors.toConcurrentMap(
                                                    Function.identity(),
                                                    (Animal animal) -> now.getYear() - animal.getBirthDate().getYear())
                                            );
                                } else {
                                    Map<Animal, Integer> oldestAnimal = new ConcurrentHashMap<>();

                                    oldestAnimal.put(firstAnimal.get(), now.getYear() - firstAnimal.get().getBirthDate().getYear());

                                    return oldestAnimal;
                                }
                            } else {
                                throw new RuntimeException();
                            }
                        }
                ));
    }

    @Override
    public Map<String, List<Animal>> findDuplicate() {
        Preconditions.checkArgument(MapUtils.isNotEmpty(animals), "Map animals is null or empty");

        return animals.entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey(),
                        entry.getValue()
                                .stream()
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                .entrySet()
                                .stream()
                                .filter(value -> value.getValue() > 1L)
                                .map(Map.Entry::getKey)
                                .collect(Collectors.collectingAndThen(Collectors.toList(), CopyOnWriteArrayList::new))))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void printDuplicates() {
        Map<String, List<Animal>> animals = findDuplicate();

        for(Map.Entry<String, List<Animal>> animalMapElement: animals.entrySet()){
            System.out.println(animalMapElement.getKey());

            for (Animal animal : animalMapElement.getValue()) {
                System.out.println(animal);
            }
        }
    }

    @Override
    public void findAverageAge(List<Animal> animals) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(animals), "List of animals is null or empty");

        final var now = LocalDate.now();

        var result = animals.stream()
                .peek(a -> Preconditions.checkAnimalField(Objects.nonNull(a.getBirthDate()), "Birthdate"))
                .map(a -> now.getYear() - a.getBirthDate().getYear())
                .mapToInt(i -> i)
                .average()
                .stream()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("\n"));

        System.out.println(result);
    }

    @Override
    public List<Animal> findOldAndExpensive(List<Animal> animals) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(animals), "List of animals is null or empty");

        final var now = LocalDate.now();

        return animals.stream()
                .peek(animal -> {
                    Preconditions.checkAnimalField(Objects.nonNull(animal.getBirthDate()), "Birthdate");
                    Preconditions.checkAnimalField(Objects.nonNull(animal.getCost()), "Cost");
                })
                .filter(a -> {
                    BigDecimal average = animals.stream()
                            .map(Animal::getCost)
                            .filter(Objects::nonNull)
                            .collect(Collectors.collectingAndThen(
                                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add),
                                    sum -> sum.divide(BigDecimal.valueOf(animals.size()), RoundingMode.HALF_UP))
                            );

                    return a.getCost().compareTo(average) >= 0;
                })
                .filter(a -> a.getBirthDate().isBefore(now.minusYears(5)))
                .collect(Collectors.collectingAndThen(Collectors.toList(), CopyOnWriteArrayList::new));
    }

    @Override
    public List<Animal> findMinConstAnimal(List<Animal> animals) throws InvalidAnimalsSizeException {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(animals), "List of animals is null or empty");
        Preconditions.checkAnimalList(animals.size() >= 3);

        return animals.stream()
                .peek(a -> {
                    Preconditions.checkAnimalField(Objects.nonNull(a.getCost()), "Cost");
                    Preconditions.checkAnimalField(Objects.nonNull(a.getName()), "Name");
                })
                .sorted(Comparator.comparing(Animal::getCost))
                .limit(3)
                .sorted(Comparator.comparing(Animal::getName))
                .collect(Collectors.collectingAndThen(Collectors.toList(), CopyOnWriteArrayList::new));
    }

    public void checkDateAndName(Map<String, List<Animal>> animals) {
        Preconditions.checkArgument(MapUtils.isNotEmpty(animals), "List of animals is null or empty");

        for (Map.Entry<String, List<Animal>> animalMapElement : animals.entrySet()) {
            for (Animal animal : animalMapElement.getValue()) {
                Preconditions.checkAnimalField(Objects.nonNull(animal.getBirthDate()), "Birthdate");
                Preconditions.checkAnimalField(Objects.nonNull(animal.getName()), "Name");
            }
        }
    }

}
