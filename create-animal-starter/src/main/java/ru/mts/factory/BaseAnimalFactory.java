package ru.mts.factory;


import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.mts.domain.animals.Animal;
import ru.mts.enums.animals.AnimalType;
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

    @Override
    public Animal createAnimal(AnimalType animalType) {
        Animal animal = null;
        switch (animalType) {
            case PET:
                PetType petType = SimpleFactory.createPetType();
                animal = petFactory.createPet(petType);

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
