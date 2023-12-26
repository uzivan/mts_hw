package ru.mts.hw3.services.hw3;

import ru.mts.hw3.animals.Animal;
import ru.mts.hw3.factory.AnimalFactory;
import ru.mts.hw3.factory.Factory;
import ru.mts.hw3.factory.animalstypes.PetFactory;
import ru.mts.hw3.factory.animalstypes.PredatorFactory;

public interface CreateAnimalService {

    default int createAnimals() {
        int count = 10;
        AnimalFactory animalFactory = new AnimalFactory(
                new PetFactory(),
                new PredatorFactory());

        Animal animal;

        int n = 0;
        while (n != count) {
            animal = animalFactory.createAnimal(Factory.createAnimalType());
            System.out.println(animal);

            n++;
        }

        return count;
    }

}