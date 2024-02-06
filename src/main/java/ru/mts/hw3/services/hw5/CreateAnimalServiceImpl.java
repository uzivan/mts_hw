package ru.mts.hw3.services.hw5;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.enums.animals.AnimalType;
import ru.mts.hw3.factory.BaseAnimalFactory;

public class CreateAnimalServiceImpl implements CreateAnimalService {
    private final BaseAnimalFactory baseAnimalFactory;
    private final AnimalType animalType;

    public CreateAnimalServiceImpl(AnimalType animalType, BaseAnimalFactory baseAnimalFactory) {
        this.animalType = animalType;
        this.baseAnimalFactory = baseAnimalFactory;
    }

    @Override
    public Animal createAnimal() {
        return baseAnimalFactory.createAnimal(animalType);
    }

}
