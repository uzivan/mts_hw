package ru.mts.hw3.services.hw6;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.enums.animals.AnimalType;
import ru.mts.hw3.factory.BaseAnimalFactory;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    private final BaseAnimalFactory baseAnimalFactory;
    private AnimalType animalType;

    public CreateAnimalServiceImpl(BaseAnimalFactory baseAnimalFactory) {
        this.baseAnimalFactory = baseAnimalFactory;
    }

    @Override
    public Animal createAnimal() {
        return baseAnimalFactory.createAnimal(animalType);
    }

}
