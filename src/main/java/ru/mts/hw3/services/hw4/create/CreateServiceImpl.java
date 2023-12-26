package ru.mts.hw3.services.hw4.create;

import ru.mts.hw3.animals.Animal;
import ru.mts.hw3.factory.AnimalFactory;
import ru.mts.hw3.factory.Factory;
import ru.mts.hw3.services.hw4.create.CreateService;

public class CreateServiceImpl implements CreateService {
    private final AnimalFactory animalFactory;

    public CreateServiceImpl(AnimalFactory animalFactory) {
        this.animalFactory = animalFactory;
    }

    @Override
    public Animal[] createAnimals() {
        int n = 10;
        Animal[] animals = new Animal[n];

        int count = 0;
        do {
            animals[count] = animalFactory.createAnimal(Factory.createAnimalType());
            count++;
        } while (count != 10);

        return animals;
    }

    public Animal[] createAnimals(int n) {
        Animal[] animals = new Animal[n];

        int i = 0;
        for (; i < n; i++) {
            animals[i] = animalFactory.createAnimal(Factory.createAnimalType());
        }

        return animals;
    }
}
