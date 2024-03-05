package ru.mts.factory;


import ru.mts.domain.animals.Animal;
import ru.mts.enums.animals.types.PetType;
import ru.mts.enums.animals.types.PredatorType;
import ru.mts.factory.animalstypes.BasePetFactory;
import ru.mts.factory.animalstypes.BasePredatorFactory;

public class BaseAnimalFactory implements AnimalFactory {

    private final BasePetFactory petFactory;
    private final BasePredatorFactory predatorFactory;

    public BaseAnimalFactory(BasePetFactory petFactory, BasePredatorFactory predatorFactory) {
        this.petFactory = petFactory;
        this.predatorFactory = predatorFactory;
    }

    public <T> Animal createAnimal(T animalType) {
        Animal animal = null;

        if (animalType instanceof PetType) {
            animal = petFactory.createPet((PetType) animalType);
        } else if (animalType instanceof PredatorType) {
            animal = predatorFactory.createPredator((PredatorType) animalType);
        } else {
            throw new UnsupportedOperationException("unsupported case");
        }

        return animal;
    }

}
