package ru.mts.hw3;

import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.factory.BaseAnimalFactory;
import ru.mts.hw3.factory.animalstypes.BasePetFactory;
import ru.mts.hw3.factory.animalstypes.BasePredatorFactory;
import ru.mts.hw3.services.hw4.create.CreateService;
import ru.mts.hw3.services.hw4.create.CreateServiceImpl;
import ru.mts.hw3.services.hw4.search.SearchService;
import ru.mts.hw3.services.hw4.search.SearchServiceImpl;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SearchService searchService = new SearchServiceImpl();

        System.out.println("animal created in 'default' createAnimals()");
        Animal[] animals1 = new CreateService(){}.createAnimals();
        for(Animal animal: animals1){
            System.out.println(animal);
        }
        System.out.println("animal birthdate in leap year");
        String[] animals1Leap = searchService.findLeapYearNames(animals1);
        for(String animalName: animals1Leap){
            System.out.println(animalName);
        }
        System.out.println("------------------------------------------------------------------");
        CreateService animalService = new CreateServiceImpl(
                new BaseAnimalFactory(
                        new BasePetFactory(),
                        new BasePredatorFactory()));


        System.out.println("animal created in createAnimals()");
        Animal[] animals2 = animalService.createAnimals();
        for(Animal animal: animals2){
            System.out.println(animal);
        }
        System.out.println("older animals");
        Animal[] animals2Older = searchService.findOlderAnimal(animals2, 1);
        for(Animal animal: animals2Older){
            System.out.println(animal);
        }
        System.out.println("--------------------------------------------------------------------");
        if (animalService instanceof CreateServiceImpl) {
            System.out.println("animal created in createAnimals(5)");
            Animal[] animals3 = ((CreateServiceImpl) animalService).createAnimals(5);
            for(Animal animal: animals3){
                System.out.println(animal);
            }
            System.out.println("duplicates ");
            Animal[] sameAnimals3 = Arrays.copyOfRange(animals3, 0, 3);
            Animal[] concatAimals = new Animal[animals3.length + sameAnimals3.length];
            System.arraycopy(animals3, 0, concatAimals, 0, animals3.length);
            System.arraycopy(sameAnimals3, 0, concatAimals, animals3.length, sameAnimals3.length);

            List<Animal> animalList = searchService.findDuplicate(concatAimals);
            if(animalList.isEmpty()){
                System.out.println("there are no publications");
            }
        }
    }

}