package ru.mts.hw3;

import ru.mts.hw3.animals.Animal;
import ru.mts.hw3.factory.AnimalSimpleFactory;

public interface CreateAnimalService {

    default int createAnimals() {
        int count = 10;

        Animal animal;

        int n = 0;
        while (n != count) {
            animal = AnimalSimpleFactory.createRandomAnimal(n);
            System.out.println(animal);

            n++;
        }

        return count;
    }

}
