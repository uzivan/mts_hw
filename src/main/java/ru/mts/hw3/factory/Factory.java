package ru.mts.hw3.factory;

import ru.mts.hw3.enums.animals.AnimalType;
import ru.mts.hw3.enums.animals.types.PetType;
import ru.mts.hw3.enums.animals.types.PredatorType;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Factory {
    public static LocalDate createRandomLocalDate(LocalDate startDate, LocalDate endDate){
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomEpochDay);
        return  randomDate;
    }

    public static AnimalType createAnimalType(){
        Random random = new Random();
        AnimalType[] animalTypes = AnimalType.values();
        int num = random.nextInt(animalTypes.length);
        return animalTypes[num];
    }
    public static PetType createPetType(){
        Random random = new Random();
        PetType[] petTypes = PetType.values();
        int num = random.nextInt(petTypes.length);
        return petTypes[num];
    }
    public static PredatorType createPredatorType(){
        Random random = new Random();
        PredatorType[] predatorTypes = PredatorType.values();
        int num = random.nextInt(predatorTypes.length);
        return predatorTypes[num];
    }
}
