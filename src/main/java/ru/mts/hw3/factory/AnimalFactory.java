package ru.mts.hw3.factory;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.enums.animals.AnimalType;

public interface AnimalFactory extends Factory{
    public Animal createAnimal(AnimalType animalType);
}
