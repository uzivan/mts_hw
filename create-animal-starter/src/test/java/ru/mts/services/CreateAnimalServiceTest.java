package ru.mts.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ReflectionUtils;
import ru.mts.config.AnimalNameConfig;
import ru.mts.config.AppTestConfiguration;

import ru.mts.config.YamlPropertySourceFactory;
import ru.mts.domain.animals.Animal;
import ru.mts.domain.animals.animalsExt.pets.petsExt.Cat;
import ru.mts.domain.animals.animalsExt.pets.petsExt.Dog;
import ru.mts.domain.animals.animalsExt.predators.predatorsExt.Panda;
import ru.mts.domain.animals.animalsExt.predators.predatorsExt.Wolf;
import ru.mts.enums.animals.AnimalType;
import ru.mts.enums.animals.types.AnimalTypeSample;
import ru.mts.enums.animals.types.PetType;
import ru.mts.enums.animals.types.PredatorType;
import ru.mts.factory.Factory;
import ru.mts.factory.animalstypes.BasePetFactory;
import ru.mts.factory.animalstypes.BasePredatorFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@EnableConfigurationProperties(value = AnimalNameConfig.class)
@TestPropertySource(factory= YamlPropertySourceFactory.class,value = "classpath:application-test.yml")
@SpringBootTest(classes = {AppTestConfiguration.class, AnimalNameConfig.class})
public class CreateAnimalServiceTest {

    @Autowired
    private AnimalNameConfig animalNameConfig;
    @Autowired
    private BasePredatorFactory basePredatorFactory;
    @Autowired
    private BasePetFactory basePetFactory;
    @Autowired
    private CreateAnimalService createAnimalService;

    @Nested
    @DisplayName("Tests names in map")
    public class testNames {

        @Test
        @DisplayName("Test Pet Name 1")
        public void testPetName1() {
            final int numberOfNamesInProperties = 0;
            final int numberOfOtherNamesInProperties = 1;

            Map<PetType, List<String>> mapNames = getNamesToFactoryForTest(PetType.values(), numberOfNamesInProperties);
            basePetFactory.setNames(mapNames);

            setBaseAnimalFactory(AnimalType.PET);

            String actualName = createAnimalService.createAnimal().getName();
            List<String> expectedNames = getNamesToCheck(PetType.values(), numberOfNamesInProperties);

            List<String> notExpectedNames = getNamesToCheck(PetType.values(), numberOfOtherNamesInProperties);

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

        @Test
        @DisplayName("Test Pet Name 2")
        public void testPetName2() {
            final int numberOfNamesInProperties = 1;
            final int numberOfOtherNamesInProperties = 0;

            Map<PetType, List<String>> mapNames = getNamesToFactoryForTest(PetType.values(), numberOfNamesInProperties);
            basePetFactory.setNames(mapNames);

            setBaseAnimalFactory(AnimalType.PET);

            String actualName = createAnimalService.createAnimal().getName();
            List<String> expectedNames = getNamesToCheck(PetType.values(), numberOfNamesInProperties);

            List<String> notExpectedNames = getNamesToCheck(PetType.values(), numberOfOtherNamesInProperties);

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

        @Test
        @DisplayName("Test Predator Name 1")
        public void testPredatorName1() {
            final int numberOfNamesInProperties = 1;
            final int numberOfOtherNamesInProperties = 0;

            Map<PredatorType, List<String>> mapNames = getNamesToFactoryForTest(PredatorType.values(), numberOfNamesInProperties);
            basePredatorFactory.setNames(mapNames);

            setBaseAnimalFactory(AnimalType.PREDATOR);

            String actualName = createAnimalService.createAnimal().getName();
            List<String> expectedNames = getNamesToCheck(PredatorType.values(), numberOfNamesInProperties);

            List<String> notExpectedNames = getNamesToCheck(PredatorType.values(), numberOfOtherNamesInProperties);

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

        @Test
        @DisplayName("Test Predator Name 2")
        public void testPredatorName2() {
            final int numberOfNamesInProperties = 0;
            final int numberOfOtherNamesInProperties = 1;

            Map<PredatorType, List<String>> mapNames = getNamesToFactoryForTest(PredatorType.values(), numberOfNamesInProperties);
            basePredatorFactory.setNames(mapNames);

            setBaseAnimalFactory(AnimalType.PREDATOR);

            String actualName = createAnimalService.createAnimal().getName();
            List<String> expectedNames = getNamesToCheck(PredatorType.values(), numberOfNamesInProperties);

            List<String> notExpectedNames = getNamesToCheck(PredatorType.values(), numberOfOtherNamesInProperties);

            assertTrue(expectedNames.contains(actualName));
            assertFalse(notExpectedNames.contains(actualName));
        }

    }

    @Nested
    @DisplayName("Test correctly create by AnimalType")
    public class TestInheritance {

        @Test
        @DisplayName("Test Inheritance 1")
        public void testInheritance1() {
            final int numberOfNamesInProperties = 0;

            final String expectedClassName = "ru.mts.domain.animals.animalsExt.pets.Pet";
            final String notExpectedClassName = "ru.mts.domain.animals.animalsExt.predators.Predator";

            Map<PetType, List<String>> mapPetNames = getNamesToFactoryForTest(PetType.values(), numberOfNamesInProperties);
            basePetFactory.setNames(mapPetNames);

            Map<PredatorType, List<String>> mapPredatorsNames = getNamesToFactoryForTest(PredatorType.values(), numberOfNamesInProperties);
            basePredatorFactory.setNames(mapPredatorsNames);

            setBaseAnimalFactory(AnimalType.PET);

            Animal animal = createAnimalService.createAnimal();

            Class<?> clazz = animal.getClass();
            Class<?> expectedClazz;
            Class<?> notExpectedClazz;

            try {
                expectedClazz = Class.forName(expectedClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class " + expectedClassName + " is absent");
            }
            try {
                notExpectedClazz = Class.forName(notExpectedClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class " + notExpectedClassName + " is absent");
            }

            assertFalse(notExpectedClazz.isAssignableFrom(clazz));
            assertTrue(expectedClazz.isAssignableFrom(clazz));
        }

        @Test
        @DisplayName("Test Inheritance 2")
        public void testInheritance2() {
            final int numberOfNamesInProperties = 0;

            final String expectedClassName = "ru.mts.domain.animals.animalsExt.predators.Predator";
            final String notExpectedClassName = "ru.mts.domain.animals.animalsExt.pets.Pet";

            Map<PetType, List<String>> mapPetNames = getNamesToFactoryForTest(PetType.values(), numberOfNamesInProperties);
            basePetFactory.setNames(mapPetNames);

            Map<PredatorType, List<String>> mapPredatorsNames = getNamesToFactoryForTest(PredatorType.values(), numberOfNamesInProperties);
            basePredatorFactory.setNames(mapPredatorsNames);

            setBaseAnimalFactory(AnimalType.PREDATOR);

            Animal animal = createAnimalService.createAnimal();

            Class<?> clazz = animal.getClass();
            Class<?> expectedClazz;
            Class<?> notExpectedClazz;

            try {
                expectedClazz = Class.forName(expectedClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class " + expectedClassName + " is absent");
            }
            try {
                notExpectedClazz = Class.forName(notExpectedClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class " + notExpectedClassName + " is absent");
            }

            assertFalse(notExpectedClazz.isAssignableFrom(clazz));
            assertTrue(expectedClazz.isAssignableFrom(clazz));
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

    }

    public <T extends Factory, P> Map<P, List<String>> getNamesToFactoryForTest(
            P[] animalTypeSamples, int numberOfTest) {

        Map<P, List<String>> names = Arrays.stream(animalTypeSamples)
                .collect(Collectors.toUnmodifiableMap(
                        Function.identity(),
                        (animalType -> {
                            String name = findNameFromProperties(((AnimalTypeSample) animalType).getTitle(), numberOfTest);
                            List<String> listWithName = new ArrayList<>();
                            listWithName.add(name);
                            return listWithName;
                        })));

        return names;
    }

    public void setBaseAnimalFactory(AnimalType animalType) {
        Field field = ReflectionUtils.findField(createAnimalService.getClass(), "animalType", AnimalType.class);

        if (Objects.isNull(field)) {
            throw new RuntimeException("Caramba, reflection not help");
        }

        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, createAnimalService, animalType);
    }

    private String findNameFromProperties(String title, int testNumber) {
        Field field = ReflectionUtils.findField(
                AnimalNameConfig.class,
                String.format("%sNames", title),
                List.class
        );

        if (Objects.nonNull(field)) {
            ReflectionUtils.makeAccessible(field);

            String testName;
            try {
                //noinspection unchecked cast
                List<String> fullNamesList = (List<String>) field.get(animalNameConfig);

                try {
                    testName = fullNamesList.get(testNumber);
                } catch (IndexOutOfBoundsException e) {
                    throw new RuntimeException("Names for " + title + " in test number " + testNumber + " miss");
                }

                return testName;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

        return null;
    }

    public <T extends Factory, P> List<String> getNamesToCheck(P[] animalTypeSamples, int numberOfTest) {

        List<String> nameList = Arrays.stream(animalTypeSamples)
                .map(animalType -> findNameFromProperties(((AnimalTypeSample) animalType).getTitle(), numberOfTest))
                .toList();

        return nameList;
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
