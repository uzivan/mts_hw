package ru.mts.factory;


import ru.mts.domain.animals.Animal;
import ru.mts.enums.animals.AnimalType;

public interface AnimalFactory extends Factory{

    public Animal createAnimal(AnimalType animalType);

}
