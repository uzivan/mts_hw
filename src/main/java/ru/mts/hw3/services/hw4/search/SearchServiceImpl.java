package ru.mts.hw3.services.hw4.search;

import ru.mts.hw3.animals.Animal;

import java.time.LocalDate;
import java.util.Arrays;

public class SearchServiceImpl implements SearchService{

    @Override
    public String[] findLeapYearNames(Animal[] animals) {
        checkDate(animals);
        String[] animalsReadyNames = Arrays.stream(animals).filter(animal -> animal.getBirthDate().isLeapYear()).map(animal -> animal.getName()).toArray(String[]::new);
        return animalsReadyNames;
    }

    @Override
    public Animal[] findOlderAnimal(Animal[] animals, int nYears) {
        checkDate(animals);
        if(nYears <=0){
            throw new IllegalArgumentException("n should be more than 0");
        }
        LocalDate currentDateMinusNYears = LocalDate.now().minusYears(nYears);

        Animal[] animalsReady = Arrays.stream(animals).filter(animal -> animal.getBirthDate().isBefore(currentDateMinusNYears)).toArray(Animal[]::new);
        return  animalsReady;
    }

    @Override
    public void findDuplicate(Animal[] animals) {
        if(animals == null){
            throw new NullPointerException("animals[] must not be null");
        }
        boolean[] duplicates = new boolean[animals.length];
        boolean isPrint;
        for(int i = 0;i<animals.length;i++){
            isPrint = true;
            if(duplicates[i] == true){
                continue;
            }
            else {
                for(int j = i+1; j< animals.length;j++){
                    if(animals[i] == animals[j]){
                        if(isPrint){
                            System.out.println(animals[i]);
                            isPrint = false;
                        }
                        duplicates[j] = true;
                    }
                }
            }
        }
    }

    public void checkDate(Animal[] animals){
        if(animals == null){
            throw new NullPointerException("animals[] must not be null");
        }
        boolean isAnyNull = Arrays.stream(animals).anyMatch(animal -> animal.getBirthDate() == null);
        if(isAnyNull){
            throw new NullPointerException("birthdate id animals must not be null");
        }
    }
}
