package ru.mts.hw3.services.hw3;

import ru.mts.hw3.animals.Animal;
import ru.mts.hw3.factory.AnimalFactory;
import ru.mts.hw3.factory.Factory;

public class CreateAnimalServiceImpl implements CreateAnimalService {
    private final AnimalFactory animalFactory;

    public CreateAnimalServiceImpl(AnimalFactory animalFactory) {
        this.animalFactory = animalFactory;
    }

    @Override
    public int createAnimals() {
        int count = 0;

        do {
            count += CreateAnimalService.super.createAnimals();
        } while (count != 10);

        return count;
    }

    public int createAnimals(int n) {
        Animal animal;

        int i = 0;
        for (; i < n; i++) {
            animal = animalFactory.createAnimal(Factory.createAnimalType());
        }

        return i;
    }

}