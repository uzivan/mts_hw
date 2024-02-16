package ru.mts.services;


import ru.mts.domain.animals.Animal;

public interface CreateAnimalService {

    String NAME = "createAnimalService";

    Animal createAnimal();

}
