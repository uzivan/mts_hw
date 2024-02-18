package ru.mts.factory;


import ru.mts.domain.animals.Animal;
import ru.mts.enums.animals.AnimalType;

public interface AnimalFactory extends Factory {

    Animal createAnimal(AnimalType animalType);

}
