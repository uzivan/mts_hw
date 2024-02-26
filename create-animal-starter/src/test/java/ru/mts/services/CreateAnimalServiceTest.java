package ru.mts.services;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mts.config.*;
import ru.mts.domain.animals.Animal;
import ru.mts.domain.animals.animalsExt.pets.petsExt.Cat;
import ru.mts.domain.animals.animalsExt.pets.petsExt.Dog;
import ru.mts.domain.animals.animalsExt.predators.predatorsExt.Panda;
import ru.mts.domain.animals.animalsExt.predators.predatorsExt.Wolf;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import java.math.RoundingMode;


@EnableConfigurationProperties({UnexpectedAnimalConfigurationProperties.class})
@SpringBootTest(classes = {
        AnimalConfigurationProperties.class,
        AutoConfiguration.class,
        UnexpectedAnimalConfigurationProperties.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yml")
public class CreateAnimalServiceTest {

    @Autowired
    private CreateAnimalService createAnimalService;
    @Autowired
    private AnimalConfigurationProperties animalConfigurationProperties;
    @Autowired
    private UnexpectedAnimalConfigurationProperties unexpectedAnimalConfigurationProperties;

    @Nested
    @DisplayName("Tests names in map")
    public class testNames {

        @Test
        @DisplayName("Test Cat, Dog, Panda, Wolf Names 1")
        public void testPetName1() {
            Animal animal = createAnimalService.createAnimal();

            String actualName = animal.getName();

            List<String> expectedNames = getExpectedCatDogPandaWolfNames();

            List<String> notExpectedNames = getUnExpectedCatDogPandaWolfNames();

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

        @Test
        @DisplayName("Test Cat, Dog, Panda, Wolf Names 2")
        public void testPetName2() {
            Animal animal = createAnimalService.createAnimal();

            String actualName = animal.getName();

            List<String> expectedNames = getExpectedCatDogPandaWolfNames();

            List<String> notExpectedNames = getUnExpectedCatDogPandaWolfNames();

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

        @Test
        @DisplayName("Test Cat, Dog, Panda, Wolf Names 3")
        public void testPetName3() {
            Animal animal = createAnimalService.createAnimal();

            String actualName = animal.getName();

            List<String> expectedNames = getExpectedCatDogPandaWolfNames();

            List<String> notExpectedNames = getUnExpectedCatDogPandaWolfNames();

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

        @Test
        @DisplayName("Test Cat, Dog, Panda, Wolf Names 4")
        public void testPetName4() {
            Animal animal = createAnimalService.createAnimal();

            String actualName = animal.getName();

            List<String> expectedNames = getExpectedCatDogPandaWolfNames();

            List<String> notExpectedNames = getUnExpectedCatDogPandaWolfNames();

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

    }

    @Nested
    @DisplayName("Task for 1 task")
    public class FirstTest {

        @Test
        @DisplayName("Tests with same parameters")
        public void equalsTest() {
            Animal animal1;
            Animal animal2;

            for (int i = 0; i < 4; i++) {
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

            for (int i = 0; i < 4; i++) {
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

            for (int i = 0; i < 4; i++) {
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

            for (int i = 0; i < 4; i++) {
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

            for (int i = 0; i < 4; i++) {
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

            for (int i = 0; i < 4; i++) {
                animal1 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(i, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2001, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }

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

    public List<String> getExpectedCatDogPandaWolfNames() {
        List<String> names = new ArrayList<>();
        names.addAll(animalConfigurationProperties.getDogNames());
        names.addAll(animalConfigurationProperties.getCatNames());
        names.addAll(animalConfigurationProperties.getWolfNames());
        names.addAll(animalConfigurationProperties.getPandaNames());
        return names;
    }

    public List<String> getUnExpectedCatDogPandaWolfNames() {
        List<String> names = new ArrayList<>();
        names.addAll(unexpectedAnimalConfigurationProperties.getDogNames());
        names.addAll(unexpectedAnimalConfigurationProperties.getCatNames());
        names.addAll(unexpectedAnimalConfigurationProperties.getWolfNames());
        names.addAll(unexpectedAnimalConfigurationProperties.getPandaNames());
        return names;
    }
}


