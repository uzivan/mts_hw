package ru.mts.hw3.services.hw3;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.factory.BaseAnimalFactory;
import ru.mts.hw3.factory.SimpleFactory;

public class CreateAnimalServiceImpl implements CreateAnimalService {
    private final BaseAnimalFactory animalFactory;

    public CreateAnimalServiceImpl(BaseAnimalFactory animalFactory) {
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
            animal = animalFactory.createAnimal(SimpleFactory.createAnimalType());
        }

        return i;
    }

}