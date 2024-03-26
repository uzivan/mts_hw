package ru.mts.factory;

import ru.mts.domain.animals.Animal;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimpleFactoryFunctions {
    public static void createRandomAnimals(Map<String, List<Animal>> animals, int n) {
        Random random = new Random();

        for(Map.Entry<String, List<Animal>> animal: animals.entrySet()) {
            int sizeAnimalList = animal.getValue().size();
            List<Animal> animalList = animal.getValue();

            for(int i = 0; i < n;i++) {
                animalList.add(
                        animalList.get(
                                random.nextInt(sizeAnimalList)
                        )
                );
            }
        }
    }
}
