package ru.mts.hw3.services.hw4.create;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.factory.BaseAnimalFactory;
import ru.mts.hw3.factory.SimpleFactory;
import ru.mts.hw3.factory.animalstypes.BasePetFactory;
import ru.mts.hw3.factory.animalstypes.BasePredatorFactory;

public interface CreateService {

    /**
     * @return randomly generated animals
     */
    default Animal[] createAnimals() {
        int count = 10;
        BaseAnimalFactory animalFactory = new BaseAnimalFactory(
                new BasePetFactory(),
                new BasePredatorFactory());

        Animal[] animals = new Animal[count];

        int n = 0;
        while (n != count) {
            animals[n] = animalFactory.createAnimal(SimpleFactory.createAnimalType());

            n++;
        }

        return animals;
    }

}
