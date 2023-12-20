package ru.mts.hw3;

public class Main {

    public static void main(String[] args) {
        System.out.println("animal created in 'default' createAnimals()");
        new CreateAnimalService() {
        }.createAnimals();

        CreateAnimalService animalService = new CreateAnimalServiceImpl();
        System.out.println("animal created in createAnimals()");
        animalService.createAnimals();

        if (animalService instanceof CreateAnimalServiceImpl) {
            System.out.println("animal created in createAnimals(5)");
            ((CreateAnimalServiceImpl) animalService).createAnimals(5);
        }

    }

}
