package ru.mts.services;

import ru.mts.domain.animals.Animal;
import ru.mts.enums.animals.AnimalType;
import ru.mts.enums.animals.types.AnimalTypeSample;
import ru.mts.factory.BaseAnimalFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAnimalServiceImpl implements CreateAnimalService {

    private final BaseAnimalFactory baseAnimalFactory;

    public CreateAnimalServiceImpl(BaseAnimalFactory baseAnimalFactory) {
        this.baseAnimalFactory = baseAnimalFactory;
    }

    @Override
    public Map<String, List<Animal>> createAnimals(int n) {
        Map<String, List<Animal>> animalMap = new HashMap<>();

        for(AnimalType animalType: AnimalType.values()) {
            AnimalTypeSample[] animalHeirType = animalType.getAnimalTypeSamples();

            for(AnimalTypeSample animalEnumExemplar: animalHeirType) {
                List<Animal> animalList = new ArrayList<>();

                for(int i = 0; i < n;i++){
                    animalList.add(baseAnimalFactory.createAnimal(animalEnumExemplar));
                }

                animalMap.put(animalEnumExemplar.getName(), animalList);
            }
        }

        return animalMap;
    }

}
