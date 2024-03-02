package ru.mts.repositories;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = ConfigApp.class)
public class AnimalRepositoryTests {
    @Autowired
    private AnimalsRepository animalsRepository;


    @Nested
    public class findOlderAnimalTests {

        @Test
        @DisplayName("Test for findOlderAnimal method")
        public void findOlderAnimalTest1() {
            int nYears = 10;

            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.now().minusYears(nYears).plusDays(3),
                    LocalDate.now().minusYears(nYears).minusDays(5),
                    LocalDate.now()
            };

            int[] indexesOfExpectedAnimals = new int[]{1};

            Animal[] animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            Animal[] expectedAnimals = createExpectedAnimalsFromIndexes(animals, indexesOfExpectedAnimals);

            Animal[] actualAnimals = animalsRepository.findOlderAnimal(nYears);

            assertArrayEquals(expectedAnimals, actualAnimals);
        }

        @Test
        @DisplayName("Test for findOlderAnimal method")
        public void findOlderAnimalTest2() {
            int nYears = 1;

            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.now().minusMonths(3),
                    LocalDate.now()
            };

            int[] indexesOfExpectedAnimals = new int[]{};

            Animal[] animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            Animal[] expectedAnimals = createExpectedAnimalsFromIndexes(animals, indexesOfExpectedAnimals);

            Animal[] actualAnimals = animalsRepository.findOlderAnimal(nYears);

            Assertions.assertArrayEquals(expectedAnimals, actualAnimals);
        }

        @Test
        @DisplayName("Test for findOlderAnimal method")
        public void findOlderAnimalTest3() {
            int nYears = 4;

            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.now().minusYears(4),
                    LocalDate.now().minusYears(4).minusDays(1)
            };

            int[] indexesOfExpectedAnimals = new int[]{1};

            Animal[] animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            Animal[] expectedAnimals = createExpectedAnimalsFromIndexes(animals, indexesOfExpectedAnimals);

            Animal[] actualAnimals = animalsRepository.findOlderAnimal(nYears);

            Assertions.assertArrayEquals(expectedAnimals, actualAnimals);
        }

        @Test
        @DisplayName("Test for findOlderAnimal method with null input")
        public void findOlderAnimalsWithNullInputTest() {
            int nYears = 5;

            setAnimalsToAnimalRepository(null);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(nYears));
        }

        @Test
        @DisplayName("Test for findOlderAnimal with null birthDay")
        public void findOlderAnimalsWithNullBirthDate() {
            int nYears = 5;
            Animal[] animals = new Animal[]{
                    new Dog("name",
                            "bread",
                            BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                            "character",
                            null)
            };

            setAnimalsToAnimalRepository(animals);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(nYears));
        }

        @Test
        @DisplayName("Test for findOlderAnimal method with incorrect N")
        public void findOlderAnimalsWithIncorrectN() {
            int n = 5;

            Animal[] animals = createAnimalsWithGivenBirthDate(
                    createRandomDate(n)
            );

            setAnimalsToAnimalRepository(animals);

            int zeroN = 0;
            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(zeroN));

            int negativeN = -5;
            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findOlderAnimal(negativeN));
        }

    }


    @Nested
    public class findLeapYearNamesTests {

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

            String[] expectedNames = new String[]{"name_0", "name_4"};

            Animal[] animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            String[] actualNames = animalsRepository.findLeapYearNames();

            Assertions.assertArrayEquals(expectedNames, actualNames);
        }

        @Test
        @DisplayName("Test for findLeapYearNames method")
        public void findLeapYearNamesTest2() {
            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.of(1600, 1, 1),
                    LocalDate.of(1900, 1, 1),
                    LocalDate.of(1904, 1, 1)
            };

            Animal[] animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            String[] expectedNames = new String[]{"name_0", "name_2"};

            String[] actualNames = animalsRepository.findLeapYearNames();

            Assertions.assertArrayEquals(expectedNames, actualNames);
        }

        @Test
        @DisplayName("Test for findLeapYearNames method")
        public void findLeapYearNamesTest3() {
            LocalDate[] birthDates = new LocalDate[]{
                    LocalDate.of(1, 1, 1),
                    LocalDate.of(2, 1, 1),
                    LocalDate.of(3, 1, 1)
            };

            Animal[] animals = createAnimalsWithGivenBirthDate(birthDates);

            setAnimalsToAnimalRepository(animals);

            String[] expectedNames = new String[0];

            String[] actualNames = animalsRepository.findLeapYearNames();

            Assertions.assertArrayEquals(expectedNames, actualNames);
        }

        @Test
        @DisplayName("Test for findLeapYearNames method with null input")
        public void findLeapYearNamesWithNullInputTest() {
            setAnimalsToAnimalRepository(null);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findLeapYearNames());
        }

        @Test
        @DisplayName("Test for findLeapYearNames method with null birthDate")
        public void findLeapYearNamesWithNullDateTest() {
            Animal[] animals = new Animal[]{
                    new Dog("name",
                            "bread",
                            BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                            "character",
                            null)
            };

            setAnimalsToAnimalRepository(animals);

            assertThrows(IllegalArgumentException.class, () -> animalsRepository.findLeapYearNames());
        }


        @Nested
        public class findDuplicateTests {

            @Test
            @DisplayName("Test for findDuplicate method")
            public void findDuplicateTest1() {
                int n = 5;

                Animal[] animals = createAnimalsWithGivenBirthDate(
                        createRandomDate(n)
                );

                Animal[] sameAnimals = Arrays.copyOfRange(animals, 0, 3);

                Animal[] concatAnimals = new Animal[animals.length + sameAnimals.length];
                System.arraycopy(animals, 0, concatAnimals, 0, animals.length);
                System.arraycopy(sameAnimals, 0, concatAnimals, animals.length, sameAnimals.length);

                setAnimalsToAnimalRepository(concatAnimals);

                Set<Animal> expectedAnimals = new HashSet<>(Arrays.asList(sameAnimals));

                Set<Animal> actualAnimals = animalsRepository.findDuplicate();

                assertTrue(actualAnimals.size() == expectedAnimals.size()
                        && actualAnimals.containsAll(expectedAnimals)
                        && expectedAnimals.containsAll(actualAnimals));
            }

            @Test
            @DisplayName("Test for findDuplicate method")
            public void findDuplicateTest2() {
                int n = 5;

                Animal[] animals = createAnimalsWithGivenBirthDate(
                        createRandomDate(n)
                );

                Animal[] sameAnimals1 = Arrays.copyOfRange(animals, 0, 3);
                Animal[] sameAnimals2 = Arrays.copyOfRange(animals, 0, 1);

                Animal[] concatAnimals = new Animal[animals.length + sameAnimals1.length + sameAnimals2.length];
                System.arraycopy(animals, 0, concatAnimals, 0, animals.length);
                System.arraycopy(sameAnimals1, 0, concatAnimals, animals.length, sameAnimals1.length);
                System.arraycopy(sameAnimals2, 0, concatAnimals, animals.length + sameAnimals1.length, sameAnimals2.length);

                setAnimalsToAnimalRepository(concatAnimals);

                Set<Animal> expectedAnimals = new HashSet<>(Arrays.asList(sameAnimals1));

                Set<Animal> actualAnimals = animalsRepository.findDuplicate();

                assertTrue(actualAnimals.size() == expectedAnimals.size()
                        && actualAnimals.containsAll(expectedAnimals)
                        && expectedAnimals.containsAll(actualAnimals));
            }

            @Test
            @DisplayName("Test for findDuplicate method")
            public void findDuplicateTest3() {
                int n = 5;

                Animal[] animals = createAnimalsWithGivenBirthDate(
                        createRandomDate(n)
                );

                setAnimalsToAnimalRepository(animals);

                Set<Animal> expectedAnimals = new HashSet<>();

                Set<Animal> actualAnimals = animalsRepository.findDuplicate();

                assertTrue(actualAnimals.size() == expectedAnimals.size()
                        && actualAnimals.containsAll(expectedAnimals)
                        && expectedAnimals.containsAll(actualAnimals));
            }

            @Test
            @DisplayName("Test for findDuplicate method with null input")
            public void findDuplicateWithNullInputTest() {
                setAnimalsToAnimalRepository(null);

                assertThrows(IllegalArgumentException.class, () -> animalsRepository.findDuplicate());
            }

        }


    }

    private LocalDate[] createRandomDate(int count){
        LocalDate[] localDates = new LocalDate[count];

        for(int i = 0; i < count; i++){
            localDates[i] = SimpleFactory.createRandomLocalDate(
                    LocalDate.of(2022, 1, 1),
                    LocalDate.of(2022, 12, 31)
            );
        }

        return localDates;
    }

    private Animal[] createExpectedAnimalsFromIndexes(Animal[] animals, int[] indexes) {
        Animal[] expectedAnimals = new Animal[indexes.length];

        for (int i = 0; i < animals.length; i++) {
            for (int j = 0; j < indexes.length; j++) {
                if (i == indexes[j]) {
                    expectedAnimals[j] = animals[indexes[j]];
                }
            }
        }

        return expectedAnimals;
    }

    private Animal[] createAnimalsWithGivenBirthDate(LocalDate[] birthDate) {
        Animal[] animals = new Animal[birthDate.length];

        for (int i = 0; i < birthDate.length; i++) {
            animals[i] = chooseAnimal(i
                    , "name_" + String.valueOf(i)
                    , "bread_" + String.valueOf(i)
                    , BigDecimal.valueOf(i).setScale(2, RoundingMode.HALF_UP)
                    , "character_" + String.valueOf(i)
                    , birthDate[i]);
        }

        return animals;
    }

    private void setAnimalsToAnimalRepository(Animal[] animals) {
        Field field = ReflectionUtils.findField(animalsRepository.getClass(), "animals", Animal[].class);
        if (Objects.isNull(field)) {
            throw new RuntimeException("Caramba, reflection not help");
        }

        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, animalsRepository, animals);
    }

    private Animal chooseAnimal(int number, String name, String bread, BigDecimal cost, String character, LocalDate birthDate) {
        Animal animal = null;

        number = number % 4;

        switch (number) {
            case 0:
                animal = new Cat(name, bread, cost, character, birthDate);
            case 1:
                animal = new Dog(name, bread, cost, character, birthDate);
            case 2:
                animal = new Panda(name, bread, cost, character, birthDate);
            case 3:
                animal = new Wolf(name, bread, cost, character, birthDate);
        }
        return animal;
    }

}
