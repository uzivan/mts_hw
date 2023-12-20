package ru.mts.hw3;

import ru.mts.hw3.animals.Animal;
import ru.mts.hw3.factory.AnimalSimpleFactory;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    @Override
    public int createAnimals() {
        int count = 0;

        do {
            // эта запись для примера
            // в случае если кол-во изменится в super методе то и реульзтат будет иной
            count += CreateAnimalService.super.createAnimals();

        } while (count != 10);

        return count;
    }

    public int createAnimals(int n) {
        Animal animal;

        int i = 0;
        for (; i < n; i++) {
            animal = AnimalSimpleFactory.createRandomAnimal(n);
            System.out.println(animal);
        }

        return i;
    }

}