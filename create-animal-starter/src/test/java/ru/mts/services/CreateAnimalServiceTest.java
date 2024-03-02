package ru.mts.services;


import org.junit.jupiter.api.BeforeAll;
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


@SpringBootTest(classes = {
        AnimalConfigurationProperties.class,
        AutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yml")
public class CreateAnimalServiceTest {

    @Autowired
    private CreateAnimalService createAnimalService;
    @Autowired
    private AnimalConfigurationProperties animalConfigurationProperties;

    @Nested
    @DisplayName("Tests names in map")
    public class testNames {

        @Test
        @DisplayName("Test Names 1")
        public void testPetName1() {
            Animal animal = createAnimalService.createAnimal();

            String actualName = animal.getName();

            List<String> expectedNames = getNamesByClass(animal);

            List<String> notExpectedNames = getUnExpectedNamesByClass(animal);

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

        @Test
        @DisplayName("Test Names 2")
        public void testPetName2() {
            Animal animal = createAnimalService.createAnimal();

            String actualName = animal.getName();

            List<String> expectedNames = getNamesByClass(animal);

            List<String> notExpectedNames = getUnExpectedNamesByClass(animal);

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

        @Test
        @DisplayName("Test Names 3")
        public void testPetName3() {
            Animal animal = createAnimalService.createAnimal();

            String actualName = animal.getName();

            List<String> expectedNames = getNamesByClass(animal);

            List<String> notExpectedNames = getUnExpectedNamesByClass(animal);

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

        @Test
        @DisplayName("Test Names 4")
        public void testPetName4() {
            Animal animal = createAnimalService.createAnimal();

            String actualName = animal.getName();

            List<String> expectedNames = getNamesByClass(animal);

            List<String> notExpectedNames = getUnExpectedNamesByClass(animal);

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
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

    private List<String> getNamesByClass(Animal animal) {
        if(animal instanceof Cat){
            return animalConfigurationProperties.getCatNames();
        }else if (animal instanceof Dog){
            return animalConfigurationProperties.getDogNames();
        }else if (animal instanceof Panda){
            return animalConfigurationProperties.getPandaNames();
        }else if (animal instanceof Wolf){
            return animalConfigurationProperties.getWolfNames();
        }
        else{
            throw new RuntimeException("It is not Cat, Dog, Panda, Wolf");
        }
    }

    private List<String> getUnExpectedNamesByClass(Animal animal) {
        if(animal instanceof Cat){
            return animalConfigurationProperties.getDogNames();
        }else if (animal instanceof Dog){
            return animalConfigurationProperties.getPandaNames();
        }else if (animal instanceof Panda){
            return animalConfigurationProperties.getWolfNames();
        }else if (animal instanceof Wolf){
            return animalConfigurationProperties.getCatNames();
        }
        else{
            throw new RuntimeException("It is not Cat, Dog, Panda, Wolf");
        }
    }
}


