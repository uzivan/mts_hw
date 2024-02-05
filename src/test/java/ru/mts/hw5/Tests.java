package ru.mts.hw5;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.domain.animals.animalsExt.pets.petsExt.Cat;
import ru.mts.hw3.domain.animals.animalsExt.pets.petsExt.Dog;
import ru.mts.hw3.domain.animals.animalsExt.predators.predatorsExt.Panda;
import ru.mts.hw3.domain.animals.animalsExt.predators.predatorsExt.Wolf;
import ru.mts.hw3.factory.SimpleFactory;
import ru.mts.hw3.services.hw4.search.SearchService;
import ru.mts.hw3.services.hw4.search.SearchServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Tests {
    private static SearchService searchService;

    @BeforeAll
    public static void beforeAll() {
        searchService = new SearchServiceImpl();
    }

    @Nested
    @DisplayName("Task for 1 task")
    public class FirstTest {
        @Test
        @DisplayName("Tests with same parameters")
        public void equalsTest() {
            Animal animal1;
            Animal animal2;

            for(int i = 0;i < 4;i++){
                animal1 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));

                assertEquals(animal1, animal2);
            }
        }
        @Test
        @DisplayName("Equals tests where different name")
        public void equalsWithDifferentNameTest() {
            Animal animal1;
            Animal animal2;

            for(int i = 0;i < 4;i++){
                animal1 = chooseAnimal(i, "name_1", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(i, "name_2", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }
        @Test
        @DisplayName("Equals tests where different bread")
        public void equalsWithDifferentBreadTest() {
            Animal animal1;
            Animal animal2;

            for(int i = 0;i < 4;i++){
                animal1 = chooseAnimal(i, "name", "bread_1", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(i, "name", "bread_2", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }
        @Test
        @DisplayName("Equals tests where different cost")
        public void equalsWithDifferentCostTest() {
            Animal animal1;
            Animal animal2;

            for(int i = 0;i < 4;i++){
                animal1 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(2).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }
        @Test
        @DisplayName("Equals tests where different character")
        public void equalsWithDifferentCharacterTest() {
            Animal animal1;
            Animal animal2;

            for(int i = 0;i < 4;i++){
                animal1 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character_1", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character_2", LocalDate.of(2000, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }
        @Test
        @DisplayName("Equals tests where different birthDate")
        public void equalsWithDifferentBirthDateTest() {
            Animal animal1;
            Animal animal2;

            for(int i = 0;i < 4;i++){
                animal1 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2001, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }
    }

    @Nested
    @DisplayName("Tests for 2, 3, 4 tasks")
    public class SecondTests {
        @Nested
        public class findLeapYearNamesTests {
            @Test
            @DisplayName("Test for findLeapYearNames method with mock")
            public void findLeapYearNamesWithMockTest(){
                Animal animal1 = Mockito.mock(Animal.class);
                Animal animal2 = Mockito.mock(Animal.class);
                Animal animal3 = Mockito.mock(Animal.class);

                when(animal1.getBirthDate()).thenReturn(LocalDate.of(2002, 1, 1));
                when(animal2.getBirthDate()).thenReturn(LocalDate.of(2000, 1, 1));
                when(animal3.getBirthDate()).thenReturn(LocalDate.of(1999, 1, 1));

                when(animal1.getName()).thenReturn("name_1");
                when(animal2.getName()).thenReturn("name_2");
                when(animal3.getName()).thenReturn("name_3");

                Animal[] animals = new Animal[] {animal1, animal2, animal3};

                String[] expectedNames = new String[] {"name_2"};

                String[] actualNames = searchService.findLeapYearNames(animals);

                Assertions.assertArrayEquals(expectedNames, actualNames);
            }

            @Test
            @DisplayName("Test for findLeapYearNames method with spy")
            public void findLeapYearNamesWithSpyTest(){
                int n = 5;

                Animal[] animals = createAnimalsWithGivenBirthDate(
                        createRandomDate(n)
                );

                for(int i = 0;i < animals.length;i++){
                    animals[i] = spy(animals[i]);
                }

                String[] actualNames = searchService.findLeapYearNames(animals);

                for(int i = 0;i < animals.length;i++){
                    verify(animals[i], atLeast(2)).getBirthDate();
                }
            }

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

                String[] actualNames = searchService.findLeapYearNames(animals);

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

                String[] expectedNames = new String[]{"name_0", "name_2"};

                String[] actualNames = searchService.findLeapYearNames(animals);

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

                String[] expectedNames = new String[0];

                String[] actualNames = searchService.findLeapYearNames(animals);

                Assertions.assertArrayEquals(expectedNames, actualNames);
            }
            @Test
            @DisplayName("Test for findLeapYearNames method with null input")
            public void findLeapYearNamesWithNullInputTest() {
                assertThrows(IllegalArgumentException.class, () -> searchService.findLeapYearNames(null));
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
                assertThrows(IllegalArgumentException.class, () -> searchService.findLeapYearNames(animals));
            }
        }
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

                Animal[] expectedAnimals = createExpectedAnimalsFromIndexes(animals, indexesOfExpectedAnimals);

                Animal[] actualAnimals = searchService.findOlderAnimal(animals, nYears);

                Assertions.assertArrayEquals(expectedAnimals, actualAnimals);
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

                Animal[] expectedAnimals = createExpectedAnimalsFromIndexes(animals, indexesOfExpectedAnimals);

                Animal[] actualAnimals = searchService.findOlderAnimal(animals, nYears);

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

                Animal[] expectedAnimals = createExpectedAnimalsFromIndexes(animals, indexesOfExpectedAnimals);

                Animal[] actualAnimals = searchService.findOlderAnimal(animals, nYears);

                Assertions.assertArrayEquals(expectedAnimals, actualAnimals);
            }

            @Test
            @DisplayName("Test for findOlderAnimal method with null input")
            public void findOlderAnimalsWithNullInputTest() {
                int nYears = 5;
                assertThrows(IllegalArgumentException.class, () -> searchService.findOlderAnimal(null, nYears));
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
                assertThrows(IllegalArgumentException.class, () -> searchService.findOlderAnimal(animals, nYears));
            }
            @Test
            @DisplayName("Test for findOlderAnimal method with incorrect N")
            public void findOlderAnimalsWithIncorrectN() {
                int n = 5;

                Animal[] animals = createAnimalsWithGivenBirthDate(
                        createRandomDate(n)
                );

                int zeroN = 0;
                assertThrows(IllegalArgumentException.class, () -> searchService.findOlderAnimal(animals, zeroN));

                int negativeN = -5;
                assertThrows(IllegalArgumentException.class, () -> searchService.findOlderAnimal(animals, negativeN));
            }
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

                List<Animal> expectedAnimals = new ArrayList<>(Arrays.asList(sameAnimals));

                List<Animal> actualAnimals = searchService.findDuplicate(concatAnimals);

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

                List<Animal> expectedAnimals = new ArrayList<>(Arrays.asList(sameAnimals1));

                List<Animal> actualAnimals = searchService.findDuplicate(concatAnimals);

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

                List<Animal> expectedAnimals = new ArrayList<>();

                List<Animal> actualAnimals = searchService.findDuplicate(animals);

                assertTrue(actualAnimals.size() == expectedAnimals.size()
                        && actualAnimals.containsAll(expectedAnimals)
                        && expectedAnimals.containsAll(actualAnimals));
            }

            @Test
            @DisplayName("Test for findDuplicate method with null input")
            public void findDuplicateWithNullInputTest() {
                assertThrows(IllegalArgumentException.class, () -> searchService.findDuplicate(null));
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
