package ru.mts.hw3.services.hw3;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.factory.BaseAnimalFactory;
import ru.mts.hw3.factory.SimpleFactory;
import ru.mts.hw3.factory.animalstypes.BasePetFactory;
import ru.mts.hw3.factory.animalstypes.BasePredatorFactory;

public interface CreateAnimalService {

    default int createAnimals() {
        int count = 10;
        BaseAnimalFactory animalFactory = new BaseAnimalFactory(
                new BasePetFactory(),
                new BasePredatorFactory());

        Animal animal;

        int n = 0;
        while (n != count) {
            animal = animalFactory.createAnimal(SimpleFactory.createAnimalType());
            System.out.println(animal);

            n++;
        }

        return count;
    }

}