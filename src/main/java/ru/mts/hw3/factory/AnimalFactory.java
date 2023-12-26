package ru.mts.hw3.factory;

import ru.mts.hw3.animals.Animal;
import ru.mts.hw3.enums.animals.AnimalType;
import ru.mts.hw3.enums.animals.types.PetType;
import ru.mts.hw3.enums.animals.types.PredatorType;
import ru.mts.hw3.factory.animalstypes.PetFactory;
import ru.mts.hw3.factory.animalstypes.PredatorFactory;

public class AnimalFactory {

    private final PetFactory petFactory;

    private final PredatorFactory predatorFactory;

    public AnimalFactory(PetFactory petFactory, PredatorFactory predatorFactory) {
        this.petFactory = petFactory;
        this.predatorFactory = predatorFactory;
    }
    public Animal createAnimal(AnimalType animalType){
        Animal animal = null;
        switch (animalType){
            case PET:
                PetType petType = Factory.createPetType();
                animal = petFactory.createPer(petType);
                break;
            case PREDATOR:
                PredatorType predatorType = Factory.createPredatorType();
                animal = predatorFactory.createPredator(predatorType);
                break;
        }
        return animal;
    }
}
