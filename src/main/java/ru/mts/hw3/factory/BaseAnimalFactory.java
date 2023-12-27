package ru.mts.hw3.factory;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.enums.animals.AnimalType;
import ru.mts.hw3.enums.animals.types.PetType;
import ru.mts.hw3.enums.animals.types.PredatorType;
import ru.mts.hw3.factory.animalstypes.BasePetFactory;
import ru.mts.hw3.factory.animalstypes.BasePredatorFactory;

public class BaseAnimalFactory implements AnimalFactory{

    private final BasePetFactory petFactory;
    private final BasePredatorFactory predatorFactory;

    public BaseAnimalFactory(BasePetFactory petFactory, BasePredatorFactory predatorFactory) {
        this.petFactory = petFactory;
        this.predatorFactory = predatorFactory;
    }

    @Override
    public Animal createAnimal(AnimalType animalType){
        Animal animal = null;
        switch (animalType){
            case PET:
                PetType petType = SimpleFactory.createPetType();
                animal = petFactory.createPer(petType);

                break;

            case PREDATOR:
                PredatorType predatorType = SimpleFactory.createPredatorType();
                animal = predatorFactory.createPredator(predatorType);

                break;

            default:
                throw new UnsupportedOperationException("unsupported case");
        }
        return animal;
    }
}
