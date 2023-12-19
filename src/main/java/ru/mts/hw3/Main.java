package ru.mts.hw3;

public class Main {
    public static void main(String[] args) {
        CreateAnimalServiceImpl animalService = new CreateAnimalServiceImpl();
        System.out.println("animal created in createAnimals()");
        animalService.createAnimals();
        System.out.println("animal created in createAnimals(5)");
        animalService.createAnimals(5);
    }
}
