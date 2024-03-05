package ru.mts.repositories;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;
import ru.mts.config.ConfigApp;
import ru.mts.domain.animals.Animal;
import ru.mts.domain.animals.animalsExt.pets.petsExt.Cat;
import ru.mts.domain.animals.animalsExt.pets.petsExt.Dog;
import ru.mts.domain.animals.animalsExt.predators.predatorsExt.Panda;
import ru.mts.domain.animals.animalsExt.predators.predatorsExt.Wolf;

import ru.mts.factory.SimpleFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ConfigApp.class)
public class AnimalRepositoryTests {

    @Autowired
    private AnimalsRepository animalsRepository;
    @Autowired
    private Map<String, Class<? extends Animal>> mapOfCorrespondenceBetweenNameAndClass;


    @Nested
    public class FindOlderAnimalTests {

        @Test
        @DisplayName("Test for findOlderAnimal method")
        public void findOlderAnimalTest1() {
            int nYears = 10;

            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.now().minusYears(nYears).plusDays(3),
                    LocalDate.now().minusYears(nYears).minusDays(5),
                    LocalDate.now()
            };

            Map<Integer, Integer> expectedAnimalsIndices = new HashMap<>();
            expectedAnimalsIndices.put(1, nYears);

            Map<String, List<Animal>> animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            Map<Animal, Integer> actualAnimals = animalsRepository.findOlderAnimal(nYears);

            Map<Animal, Integer> expectedAnimals = createExpectedAnimalsFromIndexes(animals, expectedAnimalsIndices);

            assertEquals(expectedAnimals, actualAnimals);
        }

        @Test
        @DisplayName("Test for findOlderAnimal method")
        public void findOlderAnimalTest2() {
            int nYears = 4;

            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.now().minusYears(nYears),
                    LocalDate.now().minusYears(nYears).minusDays(1)
            };

            Map<Integer, Integer> expectedAnimalsIndices = new HashMap<>();
            expectedAnimalsIndices.put(1, nYears);

            Map<String, List<Animal>> animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            Map<Animal, Integer> actualAnimals = animalsRepository.findOlderAnimal(nYears);

            Map<Animal, Integer> expectedAnimals = createExpectedAnimalsFromIndexes(animals, expectedAnimalsIndices);

            assertEquals(expectedAnimals, actualAnimals);
        }

        @Test
        @DisplayName("Test for findOlderAnimal method with oldest Animal")
        public void findOlderAnimalTest3() {
            int nYears = 4;

            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.now().minusYears(nYears),
                    LocalDate.now().minusYears(nYears).plusDays(1)
            };

            Map<Integer, Integer> expectedAnimalsIndices = new HashMap<>();
            expectedAnimalsIndices.put(0, nYears);

            Map<String, List<Animal>> animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            Map<Animal, Integer> actualAnimals = animalsRepository.findOlderAnimal(nYears);

            Map<Animal, Integer> expectedAnimals = createExpectedAnimalsFromIndexes(animals, expectedAnimalsIndices);

            assertTrue(expectedAnimals.entrySet().containsAll(actualAnimals.entrySet()));
        }

        @Test
        @DisplayName("Test for findOlderAnimal method with null input")
        public void findOlderAnimalsWithNullInputTest() {
            int nYears = 5;

            setAnimalsToAnimalRepository(null);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(nYears));
        }

        @Test
        @DisplayName("Test for findOlderAnimal method with empty input")
        public void findOlderAnimalsWithEmptyInputTest() {
            int nYears = 5;

            setAnimalsToAnimalRepository(new HashMap<>());

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(nYears));
        }

        @Test
        @DisplayName("Test for findOlderAnimal with null birthDay")
        public void findOlderAnimalsWithNullBirthDate() {
            int nYears = 5;
            Map<String, List<Animal>> animals = new HashMap<>();
            List<Animal> animalList = new ArrayList<>();
            animalList.add(
                    new Dog("name",
                            "bread",
                            BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                            "character",
                            null)
            );
            animals.put("Dog", animalList);

            setAnimalsToAnimalRepository(animals);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(nYears));
        }

        @Test
        @DisplayName("Test for findOlderAnimal with null name")
        public void findOlderAnimalsWithNullName() {
            int nYears = 5;
            Map<String, List<Animal>> animals = new HashMap<>();
            List<Animal> animalList = new ArrayList<>();
            animalList.add(
                    new Dog(null,
                            "bread",
                            BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                            "character",
                            LocalDate.of(1, 1, 1))
            );
            animals.put("Dog", animalList);

            setAnimalsToAnimalRepository(animals);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(nYears));
        }

        @Test
        @DisplayName("Test for findOlderAnimal method with incorrect N")
        public void findOlderAnimalsWithIncorrectN() {
            Map<String, List<Animal>> animals = new HashMap<>();
            List<Animal> animalList = new ArrayList<>();
            animalList.add(
                    new Dog("name",
                            "bread",
                            BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                            "character",
                            LocalDate.of(1, 1, 1))
            );
            animals.put("Dog", animalList);

            setAnimalsToAnimalRepository(animals);

            int zeroN = 0;
            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(zeroN));

            int negativeN = -5;
            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(negativeN));
        }

    }


    @Nested
    public class FindLeapYearNamesTests {

        @Test
        @DisplayName("Test for findLeapYearNames method")
        public void findLeapYearNamesTest1() {
            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.of(2000, 1, 1),
                    LocalDate.of(2001, 1, 1),
                    LocalDate.of(2002, 1, 1),
                    LocalDate.of(2003, 1, 1),
                    LocalDate.of(2004, 1, 1)
            };

            LocalDate[] expectedBirthDates = new LocalDate[]{
                    LocalDate.of(2000, 1, 1),
                    LocalDate.of(2004, 1, 1),
            };
            String[] expectedNames = new String[]{"name_0", "name_4"};
            Map<String, LocalDate> expectedMap = createExpectedMapWithNameAndDate(expectedBirthDates, expectedNames);

            Map<String, List<Animal>> animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            Map<String, LocalDate> actualMap = animalsRepository.findLeapYearNames();

            assertTrue(equalsTwoIdentityMap(expectedMap, actualMap));
        }

        @Test
        @DisplayName("Test for findLeapYearNames method")
        public void findLeapYearNamesTest2() {
            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.of(1600, 1, 1),
                    LocalDate.of(1900, 1, 1),
                    LocalDate.of(1904, 1, 1)
            };

            LocalDate[] expectedBirthDates = new LocalDate[]{
                    LocalDate.of(1600, 1, 1),
                    LocalDate.of(1904, 1, 1)
            };
            String[] expectedNames = new String[]{"name_0", "name_2"};
            Map<String, LocalDate> expectedMap = createExpectedMapWithNameAndDate(expectedBirthDates, expectedNames);

            Map<String, List<Animal>> animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            Map<String, LocalDate> actualMap = animalsRepository.findLeapYearNames();

            assertTrue(equalsTwoIdentityMap(expectedMap, actualMap));
        }

        @Test
        @DisplayName("Test for findLeapYearNames method")
        public void findLeapYearNamesTest3() {
            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.of(1, 1, 1),
                    LocalDate.of(2, 1, 1),
                    LocalDate.of(3, 1, 1)
            };

            Map<String, List<Animal>> animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            Map<String, LocalDate> actualMap = animalsRepository.findLeapYearNames();

            assertTrue(actualMap.isEmpty());
        }

        @Test
        @DisplayName("Test for findLeapYearNames method with null input")
        public void findLeapYearNamesWithNullInputTest() {
            setAnimalsToAnimalRepository(null);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findLeapYearNames());
        }

        @Test
        @DisplayName("Test for findLeapYearNames method with empty input")
        public void findLeapYearNamesWithEmptyInputTest() {
            setAnimalsToAnimalRepository(new HashMap<>());

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findLeapYearNames());
        }

        @Test
        @DisplayName("Test for findLeapYearNames with null birthDay")
        public void findLeapYearNamesWithNullBirthDate() {
            Map<String, List<Animal>> animals = new HashMap<>();
            List<Animal> animalList = new ArrayList<>();
            animalList.add(
                    new Dog("name",
                            "bread",
                            BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                            "character",
                            null)
            );
            animals.put("Dog", animalList);

            setAnimalsToAnimalRepository(animals);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findLeapYearNames());
        }

        @Test
        @DisplayName("Test for findLeapYearNames with null name")
        public void findLeapYearNamesWithNullName() {
            Map<String, List<Animal>> animals = new HashMap<>();
            List<Animal> animalList = new ArrayList<>();
            animalList.add(
                    new Dog(null,
                            "bread",
                            BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                            "character",
                            LocalDate.of(1, 1, 1))
            );
            animals.put("Dog", animalList);

            setAnimalsToAnimalRepository(animals);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findLeapYearNames());
        }

    }


    @Nested
    public class FindDuplicateTests {

        @Test
        @DisplayName("Test for findDuplicate method")
        public void findDuplicateTest1() {
            int n = 5;
            int nDuplicates1 = 3;
            int nDuplicates2 = 1;

            Map<String, List<Animal>> animals = createAnimalsWithGivenBirthDate(
                    createRandomDate(n)
            );

            createNDuplicates(animals, animals, nDuplicates1);
            createNDuplicates(animals, animals, nDuplicates2);

            setAnimalsToAnimalRepository(animals);

            Map<String, List<Animal>> actualMap = animalsRepository.findDuplicate();

            Map<String, List<Animal>> expectedMap = createMapWithAnimalTypesAndEmptyAnimalList();
            createNDuplicates(expectedMap, animals, nDuplicates1);

            assertEquals(expectedMap.keySet(), actualMap.keySet());

            for (Map.Entry<String, List<Animal>> animal : expectedMap.entrySet()) {
                List<Animal> expectedList = animal.getValue();
                List<Animal> actualList = actualMap.get(animal.getKey());

                assertTrue(expectedList.size() == actualList.size() && actualList.containsAll(expectedList) && expectedList.containsAll(actualList));

                actualMap.remove(animal.getKey());
            }
        }

        @Test
        @DisplayName("Test for findDuplicate method")
        public void findDuplicateTest2() {
            int n = 5;
            int nDuplicates = 3;

            Map<String, List<Animal>> animals = createAnimalsWithGivenBirthDate(
                    createRandomDate(n)
            );

            createNDuplicates(animals, animals, nDuplicates);

            setAnimalsToAnimalRepository(animals);

            Map<String, List<Animal>> actualMap = animalsRepository.findDuplicate();

            Map<String, List<Animal>> expectedMap = createMapWithAnimalTypesAndEmptyAnimalList();
            createNDuplicates(expectedMap, animals, nDuplicates);

            assertEquals(expectedMap.keySet(), actualMap.keySet());

            for (Map.Entry<String, List<Animal>> animal : expectedMap.entrySet()) {
                List<Animal> expectedList = animal.getValue();
                List<Animal> actualList = actualMap.get(animal.getKey());

                assertTrue(expectedList.size() == actualList.size() && actualList.containsAll(expectedList) && expectedList.containsAll(actualList));

                actualMap.remove(animal.getKey());
            }
        }

        @Test
        @DisplayName("Test for findDuplicate method")
        public void findDuplicateTest3() {
            int n = 5;
            int nDuplicates = 0;

            Map<String, List<Animal>> animals = createAnimalsWithGivenBirthDate(
                    createRandomDate(n)
            );

            setAnimalsToAnimalRepository(animals);

            Map<String, List<Animal>> actualMap = animalsRepository.findDuplicate();

            Map<String, List<Animal>> expectedMap = createMapWithAnimalTypesAndEmptyAnimalList();

            assertEquals(expectedMap.keySet(), actualMap.keySet());

            for (Map.Entry<String, List<Animal>> animal : expectedMap.entrySet()) {
                List<Animal> expectedList = animal.getValue();
                List<Animal> actualList = actualMap.get(animal.getKey());

                assertTrue(expectedList.size() == actualList.size() && actualList.containsAll(expectedList) && expectedList.containsAll(actualList));

                actualMap.remove(animal.getKey());
            }
        }

        @Test
        @DisplayName("Test for findLeapYearNames method with null input")
        public void findDuplicateWithNullInputTest() {
            setAnimalsToAnimalRepository(null);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findDuplicate());
        }

        @Test
        @DisplayName("Test for findLeapYearNames method with empty input")
        public void findDuplicateWithEmptyInputTest() {
            setAnimalsToAnimalRepository(new HashMap<>());

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findDuplicate());
        }

    }

    @Nested
    @DisplayName("Tests FindOldAndExpensive")
    public class FindOldAndExpensiveTests {
        @Test
        @DisplayName("Test for findOldAndExpensive 1")
        public void findOldAndExpensiveTest1() {
            final int nYears = 5;
            LocalDate[] birthdate = new LocalDate[]{
                    LocalDate.now().minusYears(nYears).minusDays(5),
                    LocalDate.now().minusYears(nYears).plusDays(5),
                    LocalDate.now().minusYears(nYears).minusDays(7)
            };
            BigDecimal[] cost = new BigDecimal[]{
                    BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP)
            };
            Integer[] indexesExpectedAnimals = new Integer[]{2};

            Map<String, List<Animal>> animalMap = createAnimalsWithDateAndCost(cost, birthdate);
            List<Animal> expectedAnimals = createExpectedAnimalsFromIndexes(animalMap, indexesExpectedAnimals);

            List<Animal> animals = animalMap.values().stream().flatMap(List::stream).toList();

            List<Animal> actualAnimals = animalsRepository.findOldAndExpensive(animals);

            assertTrue(actualAnimals.containsAll(expectedAnimals) && expectedAnimals.containsAll(actualAnimals));
        }

        @Test
        @DisplayName("Test for findOldAndExpensive 2")
        public void findOldAndExpensiveTest2() {
            final int nYears = 5;
            LocalDate[] birthdate = new LocalDate[]{
                    LocalDate.now().minusYears(nYears).minusDays(5),
                    LocalDate.now().minusYears(nYears).plusDays(5),
                    LocalDate.now().minusYears(nYears).minusDays(7),
                    LocalDate.now().minusYears(nYears).minusDays(3),
                    LocalDate.now().minusYears(nYears).minusDays(2)
            };
            BigDecimal[] cost = new BigDecimal[]{
                    BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(12).setScale(2, RoundingMode.HALF_UP)
            };
            Integer[] indexesExpectedAnimals = new Integer[]{2, 4};

            Map<String, List<Animal>> animalMap = createAnimalsWithDateAndCost(cost, birthdate);
            List<Animal> expectedAnimals = createExpectedAnimalsFromIndexes(animalMap, indexesExpectedAnimals);

            List<Animal> animals = animalMap.values().stream().flatMap(List::stream).toList();

            List<Animal> actualAnimals = animalsRepository.findOldAndExpensive(animals);

            assertTrue(actualAnimals.containsAll(expectedAnimals) && expectedAnimals.containsAll(actualAnimals));
        }

        @Test
        @DisplayName("Test for findOldAndExpensive 3")
        public void findOldAndExpensiveTest3() {
            final int nYears = 5;
            LocalDate[] birthdate = new LocalDate[]{
                    LocalDate.now().minusYears(nYears).minusDays(5),
                    LocalDate.now().minusYears(nYears).plusDays(5),
                    LocalDate.now().minusYears(nYears).minusDays(7),
                    LocalDate.now().minusYears(nYears).minusDays(3),
                    LocalDate.now().minusYears(nYears).minusDays(2)
            };
            BigDecimal[] cost = new BigDecimal[]{
                    BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                    BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP)
            };
            Integer[] indexesExpectedAnimals = new Integer[0];

            Map<String, List<Animal>> animalMap = createAnimalsWithDateAndCost(cost, birthdate);
            List<Animal> expectedAnimals = createExpectedAnimalsFromIndexes(animalMap, indexesExpectedAnimals);

            List<Animal> animals = animalMap.values().stream().flatMap(List::stream).toList();

            List<Animal> actualAnimals = animalsRepository.findOldAndExpensive(animals);

            assertTrue(actualAnimals.containsAll(expectedAnimals) && expectedAnimals.containsAll(actualAnimals));
        }

        @Test
        @DisplayName("Test for findOldAndExpensive with empty animalsList")
        public void findOldAndExpensiveTest4() {
            List<Animal> animals = new ArrayList<>();

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOldAndExpensive(animals));
        }

        @Test
        @DisplayName("Test for findOldAndExpensive with null animalsList")
        public void findOldAndExpensiveTest5() {
            List<Animal> animals = null;

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOldAndExpensive(animals));
        }

        @Test
        @DisplayName("Test for findOldAndExpensive with null costs")
        public void findOldAndExpensiveTest6() {
            List<Animal> animals = new ArrayList<>();
            animals.add(
                    new Dog("name",
                            "bread",
                            null,
                            "character",
                            LocalDate.of(1, 1, 1))
            );

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOldAndExpensive(animals));
        }

        @Test
        @DisplayName("Test for findOldAndExpensive with null birthdate")
        public void findOldAndExpensiveTest7() {
            List<Animal> animals = new ArrayList<>();
            animals.add(
                    new Dog("name",
                            "bread",
                            BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                            "character",
                            null)
            );

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOldAndExpensive(animals));
        }
    }

    private Map<String, List<Animal>> createAnimalsWithDateAndCost(BigDecimal[] costs, LocalDate[] birthDate) {
        if (costs.length != birthDate.length) {
            throw new RuntimeException("Costs length not equals to dates length");
        }

        Map<String, List<Animal>> animalMap = new HashMap<>();

        for (Map.Entry<String, Class<? extends Animal>> animalType : mapOfCorrespondenceBetweenNameAndClass.entrySet()) {
            List<Animal> animalList = new ArrayList<>();

            for (int i = 0; i < costs.length; i++) {
                try {
                    Constructor<?> constructor = animalType.getValue().getDeclaredConstructor(
                            String.class,
                            String.class,
                            BigDecimal.class,
                            String.class,
                            LocalDate.class
                    );

                    Animal animal = (Animal) constructor.newInstance(
                            "name_" + String.valueOf(i)
                            , animalType.getKey() + "_bread_" + String.valueOf(i)
                            , costs[i]
                            , "character_" + String.valueOf(i)
                            , birthDate[i]
                    );

                    animalList.add(animal);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

            animalMap.put(animalType.getKey(), animalList);
        }
        return animalMap;
    }

    private Map<String, List<Animal>> createMapWithAnimalTypesAndEmptyAnimalList() {

        Map<String, List<Animal>> expectedMap = new HashMap<>();

        for (String animalType : mapOfCorrespondenceBetweenNameAndClass.keySet()) {
            expectedMap.put(animalType, new ArrayList<Animal>());
        }

        return expectedMap;
    }

    private void createNDuplicates(Map<String, List<Animal>> takeAnimals, Map<String, List<Animal>> giveAnimals, int n) {

        for (Map.Entry<String, List<Animal>> animalMapElement : giveAnimals.entrySet()) {
            List<Animal> giveAnimalList = animalMapElement.getValue();
            List<Animal> takeAnimalList = takeAnimals.get(animalMapElement.getKey());

            for (int i = 0; i < n; i++) {
                takeAnimalList.add(giveAnimalList.get(i));
            }
        }
    }

    private LocalDate[] createRandomDate(int count) {
        LocalDate[] localDates = new LocalDate[count];

        for (int i = 0; i < count; i++) {
            localDates[i] = SimpleFactory.createRandomLocalDate(
                    LocalDate.of(2022, 1, 1),
                    LocalDate.of(2022, 12, 31)
            );
        }

        return localDates;
    }

    private boolean equalsTwoIdentityMap(Map<String, LocalDate> map1, Map<String, LocalDate> map2) {
        for (Map.Entry<String, LocalDate> map1Element : map1.entrySet()) {
            for (Map.Entry<String, LocalDate> map2Element : map2.entrySet()) {
                if (map1Element.getKey().equals(map2Element.getKey()) && map1Element.getValue().isEqual(map2Element.getValue())) {
                    map2.remove(map2Element.getKey());
                    break;
                }
            }
        }

        return map2.isEmpty();
    }

    private Map<Animal, Integer> createExpectedAnimalsFromIndexes(Map<String, List<Animal>> animals, Map<Integer, Integer> animalIndices) {
        Map<Animal, Integer> result = new HashMap<>();

        for (Map.Entry<Integer, Integer> indicesMapElement : animalIndices.entrySet()) {
            for (Map.Entry<String, List<Animal>> animalMapElement : animals.entrySet()) {
                result.put(
                        animalMapElement.getValue().get(indicesMapElement.getKey()),
                        indicesMapElement.getValue()
                );
            }
        }

        return result;
    }

    private List<Animal> createExpectedAnimalsFromIndexes(Map<String, List<Animal>> animals, Integer[] animalIndices) {
        List<Animal> result = new ArrayList<>();

        for (Integer indicesElement : animalIndices) {
            for (Map.Entry<String, List<Animal>> animalMapElement : animals.entrySet()) {
                result.add(animalMapElement.getValue().get(indicesElement));
            }
        }

        return result;
    }

    private Map<String, LocalDate> createExpectedMapWithNameAndDate(LocalDate[] dates, String[] names) {
        if (dates.length != names.length) {
            throw new RuntimeException("Length of names not equals names length");
        }

        int length = dates.length;

        Map<String, LocalDate> result = new IdentityHashMap<>();

        for (String animalType : mapOfCorrespondenceBetweenNameAndClass.keySet()) {
            for (int i = 0; i < length; i++) {
                result.put(
                        new String(animalType + " " + names[i]),
                        dates[i]
                );
            }
        }

        return result;
    }

    private Map<String, List<Animal>> createAnimalsWithGivenBirthDate(LocalDate[] birthDate) {
        Map<String, List<Animal>> animalMap = new HashMap<>();

        for (Map.Entry<String, Class<? extends Animal>> animalType : mapOfCorrespondenceBetweenNameAndClass.entrySet()) {
            List<Animal> animalList = new ArrayList<>();

            for (int i = 0; i < birthDate.length; i++) {
                try {
                    Constructor<?> constructor = animalType.getValue().getDeclaredConstructor(
                            String.class,
                            String.class,
                            BigDecimal.class,
                            String.class,
                            LocalDate.class
                    );

                    Animal animal = (Animal) constructor.newInstance(
                            "name_" + String.valueOf(i)
                            , animalType.getKey() + "_bread_" + String.valueOf(i)
                            , BigDecimal.valueOf(i).setScale(2, RoundingMode.HALF_UP)
                            , "character_" + String.valueOf(i)
                            , birthDate[i]
                    );

                    animalList.add(animal);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }

            }

            animalMap.put(animalType.getKey(), animalList);
        }

        return animalMap;
    }

    private void setAnimalsToAnimalRepository(Map<String, List<Animal>> animals) {
        Field field = ReflectionUtils.findField(animalsRepository.getClass(), "animals", Map.class);
        if (Objects.isNull(field)) {
            throw new RuntimeException("Caramba, reflection not help");
        }

        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, animalsRepository, animals);
    }

}
