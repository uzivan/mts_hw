package ru.mts.repositories;


import org.springframework.beans.factory.ObjectProvider;
import ru.mts.domain.animals.Animal;
import ru.mts.services.CreateAnimalService;
import ru.mts.utils.Preconditions;
import ru.mts.utils.exceptions.InvalidAnimalsSizeException;
import java.util.concurrent.ConcurrentHashMap;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
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
    }

    @Override
    public Map<String, LocalDate> findLeapYearNames() {
        checkDateAndName(animals);

        return animals.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(
                        animal -> new AbstractMap.SimpleEntry<>(
                                new String(entry.getKey() + " " + animal.getName()),
                                animal.getBirthDate())))
                .filter(animal -> animal.getValue().isLeapYear())
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        (existingValue, newValue) -> existingValue,
                        () -> Collections.synchronizedMap(new HashMap<>()))
                );
    }

    @Override
    public Map<Animal, Integer> findOlderAnimal(int nYears) {
        Preconditions.checkYears(nYears > 0);
        Preconditions.checkArgument(!Objects.isNull(animals), "Map of animals must not be null");
        Preconditions.checkArgument(!animals.isEmpty(), "Map of animals must not be empty");

        return animals.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .peek(animal -> {
                    Preconditions.checkAnimalField(!Objects.isNull(animal.getBirthDate()), "Birthdate");
                    Preconditions.checkAnimalField(!Objects.isNull(animal.getName()), "Name");
                })
                .sorted(Comparator.comparing(Animal::getBirthDate))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            Optional<Animal> firstAnimal = list.stream().findFirst();

                            if (firstAnimal.isPresent()) {
                                if (firstAnimal.get().getBirthDate().isBefore(LocalDate.now().minusYears(nYears))) {
                                    return list.stream().filter(animal -> animal.getBirthDate()
                                                    .isBefore(LocalDate.now().minusYears(nYears)))
                                            .collect(Collectors.toConcurrentMap(Function.identity(),
                                                    (Animal animal) -> LocalDate.now().getYear() - animal.getBirthDate().getYear()));
                                } else {
                                    ConcurrentMap<Animal, Integer> oldestAnimal = new ConcurrentHashMap<>();

                                    oldestAnimal.put(firstAnimal.get(), LocalDate.now().getYear() - firstAnimal.get().getBirthDate().getYear());

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
        Preconditions.checkArgument(!Objects.isNull(animals), "Map of animals must not be empty");
        Preconditions.checkArgument(!animals.isEmpty(), "Map of animals must not be empty");

        return animals.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(),
                        entry.getValue().stream()
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                .entrySet().stream()
                                .filter(value -> value.getValue() > 1)
                                .map(Map.Entry::getKey)
                                .collect(Collectors.collectingAndThen(Collectors.toList(), CopyOnWriteArrayList::new))))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void printDuplicates(Collection<Animal> animals) {
        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }

    @Override
    public void findAverageAge(List<Animal> animals) {
        Preconditions.checkArgument(!Objects.isNull(animals), "List of animals must not be null");
        Preconditions.checkArgument(!animals.isEmpty(), "List of animals must not be empty");

        animals.stream()
                .peek(animal -> {
                    Preconditions.checkAnimalField(!Objects.isNull(animal.getBirthDate()), "Birthdate");
                })
                .map(animal -> LocalDate.now().getYear() - animal.getBirthDate().getYear())
                .mapToInt(e -> e).average().stream().forEach(System.out::println);
    }

    @Override
    public List<Animal> findOldAndExpensive(List<Animal> animals) {
        Preconditions.checkArgument(!Objects.isNull(animals), "List of animals must not be null");
        Preconditions.checkArgument(!animals.isEmpty(), "List of animals must not be empty");

        return animals.stream()
                .peek(animal -> {
                    Preconditions.checkAnimalField(!Objects.isNull(animal.getBirthDate()), "Birthdate");
                    Preconditions.checkAnimalField(!Objects.isNull(animal.getCost()), "Cost");
                })
                .filter(animal -> {
                    BigDecimal average = animals.stream().map(Animal::getCost).collect(Collectors.collectingAndThen(
                            Collectors.reducing(BigDecimal.ZERO, BigDecimal::add),
                            sum -> sum.divide(BigDecimal.valueOf(animals.size()), RoundingMode.HALF_UP)));

                    return animal.getCost().compareTo(average) >= 0;
                })
                .filter(animal -> animal.getBirthDate().isBefore(LocalDate.now().minusYears(5)))
                .collect(Collectors.collectingAndThen(Collectors.toList(), CopyOnWriteArrayList::new));
    }

    @Override
    public List<Animal> findMinConstAnimal(List<Animal> animals) throws InvalidAnimalsSizeException {
        Preconditions.checkArgument(!Objects.isNull(animals), "List of animals must not be null");
        Preconditions.checkArgument(!animals.isEmpty(), "List of animals must not be empty");
        Preconditions.checkAnimalList(animals.size() >= 3);

        return animals.stream()
                .peek(animal -> {
                    Preconditions.checkAnimalField(!Objects.isNull(animal.getCost()), "Cost");
                    Preconditions.checkAnimalField(!Objects.isNull(animal.getName()), "Name");
                })
                .sorted(Comparator.comparing(Animal::getCost))
                .limit(3).sorted(Comparator.comparing(Animal::getName))
                .collect(Collectors.collectingAndThen(Collectors.toList(), CopyOnWriteArrayList::new));
    }

    public void checkDateAndName(Map<String, List<Animal>> animals) {
        Preconditions.checkArgument(!Objects.isNull(animals), "Map of animals must not be null");
        Preconditions.checkArgument(!animals.isEmpty(), "Map of animals must not be empty");

        for (Map.Entry<String, List<Animal>> animalMapElement : animals.entrySet()) {
            for (Animal animal : animalMapElement.getValue()) {
                Preconditions.checkAnimalField(!Objects.isNull(animal.getBirthDate()), "Birthdate");
                Preconditions.checkAnimalField(!Objects.isNull(animal.getName()), "Name");
            }
        }
    }
}
