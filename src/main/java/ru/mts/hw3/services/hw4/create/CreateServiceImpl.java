package ru.mts.hw3.services.hw4.create;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.factory.BaseAnimalFactory;
import ru.mts.hw3.factory.SimpleFactory;

public class CreateServiceImpl implements CreateService {
    private final BaseAnimalFactory animalFactory;

    public CreateServiceImpl(BaseAnimalFactory animalFactory) {
        this.animalFactory = animalFactory;
    }

    @Override
    public Animal[] createAnimals() {
        int n = 10;
        Animal[] animals = new Animal[n];

        int count = 0;
        do {
            animals[count] = animalFactory.createAnimal(SimpleFactory.createAnimalType());

            count++;
        } while (count != 10);

        return animals;
    }

    public Animal[] createAnimals(int n) {
        Animal[] animals = new Animal[n];

        int i = 0;
        for (; i < n; i++) {
            animals[i] = animalFactory.createAnimal(SimpleFactory.createAnimalType());
        }

        return animals;
    }
}
