package ru.mts.services;


import ru.mts.domain.animals.Animal;
import ru.mts.enums.animals.AnimalType;
import ru.mts.factory.BaseAnimalFactory;

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
