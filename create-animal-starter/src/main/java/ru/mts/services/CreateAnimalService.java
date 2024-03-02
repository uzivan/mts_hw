package ru.mts.services;

import ru.mts.domain.animals.Animal;

import java.util.List;
import java.util.Map;

public interface CreateAnimalService {

    String NAME = "createAnimalService";

    public Map<String, List<Animal>> createAnimals(int n);

}
