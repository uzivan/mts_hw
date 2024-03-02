package ru.mts.services;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {AnimalConfigurationProperties.class, AutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yml")
public class CreateAnimalServiceTest {

    @Autowired
    private CreateAnimalService createAnimalService;
    @Autowired
    private AnimalConfigurationProperties animalConfigurationProperties;

    @Nested
    @DisplayName("Tests names of Cat, Dog, Wolf, Panda in map")
    public class testNames {

        private Map<String, List<Animal>> animals;

        @BeforeEach
        public void initEach() {
            animals = createAnimalService.createAnimals(5);
        }

        @Test
        @DisplayName("Test cats names")
        public  void testCatName() {
            List<Animal> cats = animals.get("Cat");

            List<String> extendNames = animalConfigurationProperties.getCatNames();
            List<String> unExtendedNames = animalConfigurationProperties.getDogNames();

            for(Animal animal: cats) {
                String actualName = animal.getName();

                assertFalse(unExtendedNames.contains(actualName));
                assertTrue(extendNames.contains(actualName));
            }
        }

        @Test
        @DisplayName("Test dogs names")
        public  void testDogName() {
            List<Animal> dogs = animals.get("Dog");

            List<String> extendNames = animalConfigurationProperties.getDogNames();
            List<String> unExtendNames = animalConfigurationProperties.getPandaNames();

            for(Animal animal: dogs) {
                String actualName = animal.getName();

                assertFalse(unExtendNames.contains(actualName));
                assertTrue(extendNames.contains(actualName));
            }
        }

        @Test
        @DisplayName("Test pandas names")
        public  void testPandaName() {
            List<Animal> pandas = animals.get("Panda");

            List<String> extendNames = animalConfigurationProperties.getPandaNames();
            List<String> unExtendNames = animalConfigurationProperties.getWolfNames();

            for(Animal animal: pandas) {
                String actualName = animal.getName();

                assertFalse(unExtendNames.contains(actualName));
                assertTrue(extendNames.contains(actualName));
            }
        }

        @Test
        @DisplayName("Test wolfs names")
        public  void testWolfName() {
            List<Animal> wolfs = animals.get("Wolf");

            List<String> extendNames = animalConfigurationProperties.getWolfNames();
            List<String> unExtendNames = animalConfigurationProperties.getCatNames();

            for(Animal animal: wolfs) {
                String actualName = animal.getName();

                assertTrue(extendNames.contains(actualName));
                assertFalse(unExtendNames.contains(actualName));
            }
        }

    }


    @Nested
    @DisplayName("Equals animals tests")
    public class EqualsTest {

        private static List<String> testAnimals;

        @BeforeAll
        public static void init() {
            testAnimals = new ArrayList<>();
            testAnimals.add("Cat");
            testAnimals.add("Dog");
            testAnimals.add("Panda");
            testAnimals.add("Wolf");
        }

        @Test
        @DisplayName("Tests with same parameters")
        public void equalsTest() {
            Animal animal1;
            Animal animal2;

            for (String animalType: testAnimals) {
                animal1 = chooseAnimal(animalType, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(animalType, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));

                assertEquals(animal1, animal2);
            }
        }

        @Test
        @DisplayName("Equals tests where different name")
        public void equalsWithDifferentNameTest() {
            Animal animal1;
            Animal animal2;

            for (String animalType: testAnimals) {
                animal1 = chooseAnimal(animalType, "name_1", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(animalType, "name_2", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }

        @Test
        @DisplayName("Equals tests where different bread")
        public void equalsWithDifferentBreadTest() {
            Animal animal1;
            Animal animal2;

            for (String animalType: testAnimals) {
                animal1 = chooseAnimal(animalType, "name", "bread_1", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(animalType, "name", "bread_2", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }

        @Test
        @DisplayName("Equals tests where different cost")
        public void equalsWithDifferentCostTest() {
            Animal animal1;
            Animal animal2;

            for (String animalType: testAnimals) {
                animal1 = chooseAnimal(animalType, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(animalType, "name", "bread", BigDecimal.valueOf(2).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }

        @Test
        @DisplayName("Equals tests where different character")
        public void equalsWithDifferentCharacterTest() {
            Animal animal1;
            Animal animal2;

            for (String animalType: testAnimals) {
                animal1 = chooseAnimal(animalType, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character_1", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(animalType, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character_2", LocalDate.of(2000, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }

        @Test
        @DisplayName("Equals tests where different birthDate")
        public void equalsWithDifferentBirthDateTest() {
            Animal animal1;
            Animal animal2;

            for (String animalType: testAnimals) {
                animal1 = chooseAnimal(animalType, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2000, 1, 1));
                animal2 = chooseAnimal(animalType, "name", "bread", BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP),
                        "character", LocalDate.of(2001, 1, 1));

                assertNotEquals(animal1, animal2);
            }
        }

    }

    private Animal chooseAnimal(String animalType, String name, String bread, BigDecimal cost, String character, LocalDate birthDate) {
        Animal animal = null;

        switch (animalType) {
            case "Cat":
                animal = new Cat(name, bread, cost, character, birthDate);
            case "Dog":
                animal = new Dog(name, bread, cost, character, birthDate);
            case "Panda":
                animal = new Panda(name, bread, cost, character, birthDate);
            case "Wolf":
                animal = new Wolf(name, bread, cost, character, birthDate);
        }

        return animal;
    }

}


