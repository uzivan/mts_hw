package ru.mts.hw3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mts.hw3.config.ConfigurationApp;
import ru.mts.hw3.domain.animals.Animal;
import ru.mts.hw3.repositories.AnimalsRepository;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationApp.class);

        AnimalsRepository animalsRepository = context.getBean(AnimalsRepository.class);

        System.out.println("рожденные в високосный год");
        String[] animalsFunc1 = animalsRepository.findLeapYearNames();
        for (int i = 0; i < animalsFunc1.length; i++) {
            System.out.println(animalsFunc1[i]);
        }

        System.out.println("животные которые старше 1 года");
        Animal[] animalsFunc2 = animalsRepository.findOlderAnimal(1);
        for (int i = 0; i < animalsFunc2.length; i++) {
            System.out.println(animalsFunc2[i]);
        }

        System.out.println("дубликаты");
        Animal[] animalsFunc3 = animalsRepository.findOlderAnimal(1);
    }

}