package ru.mts.scheduled;

import java.text.SimpleDateFormat;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.mts.config.ConfigApp;
import ru.mts.domain.animals.Animal;
import ru.mts.repositories.AnimalsRepository;

@Component
public class ScheduledTasks {

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigApp.class);
        AnimalsRepository animalsRepository = context.getBean(AnimalsRepository.class);

        System.out.println();

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
        Set<Animal> animalsFunc3 = animalsRepository.findDuplicate();
        animalsRepository.printDuplicates(animalsFunc3);
    }

}

