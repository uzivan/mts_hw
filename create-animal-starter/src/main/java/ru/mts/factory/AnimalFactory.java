package ru.mts.factory;


import ru.mts.domain.animals.Animal;

public interface AnimalFactory extends Factory {

    public <T> Animal createAnimal(T animalType);

}
