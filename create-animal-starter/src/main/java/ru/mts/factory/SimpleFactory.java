package ru.mts.factory;

import ru.mts.enums.animals.AnimalType;
import ru.mts.enums.animals.types.PetType;
import ru.mts.enums.animals.types.PredatorType;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleFactory {

    public static LocalDate createRandomLocalDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomEpochDay);

        return randomDate;
    }

    public static AnimalType createAnimalType() {
        Random random = new Random();
        AnimalType[] animalTypes = AnimalType.values();
        int num = random.nextInt(animalTypes.length);

        return animalTypes[num];
    }

    public static PetType createPetType() {
        Random random = new Random();
        PetType[] petTypes = PetType.values();
        int num = random.nextInt(petTypes.length);

        return petTypes[num];
    }

    public static PredatorType createPredatorType() {
        Random random = new Random();
        PredatorType[] predatorTypes = PredatorType.values();
        int num = random.nextInt(predatorTypes.length);

        return predatorTypes[num];
    }

    public static String chooseNameFromArray(List<String> names) {
        Random random = new Random();
        int num = random.nextInt(names.size());

        return names.get(num);
    }

}
