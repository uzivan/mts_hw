package ru.mts.hw3.services.hw4.create;

import ru.mts.hw3.animals.Animal;
import ru.mts.hw3.factory.AnimalFactory;
import ru.mts.hw3.factory.Factory;
import ru.mts.hw3.factory.animalstypes.PetFactory;
import ru.mts.hw3.factory.animalstypes.PredatorFactory;

public interface CreateService {
    /**
     * @return randomly generated animals
     */
    default Animal[] createAnimals() {
        int count = 10;
        AnimalFactory animalFactory = new AnimalFactory(
                new PetFactory(),
                new PredatorFactory());

        Animal[] animals = new Animal[count];

        int n = 0;
        while (n != count) {
            animals[n] = animalFactory.createAnimal(Factory.createAnimalType());
            n++;
        }
        return animals;
    }
}
