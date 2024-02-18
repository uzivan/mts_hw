package ru.mts.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mts.domain.animals.Animal;
import ru.mts.repositories.AnimalsRepository;

import java.util.Set;

@Component(ScheduledTasksMBean.NAME)
public class ScheduledTasks implements ScheduledTasksMBean {

    private final AnimalsRepository animalsRepository;

    @Autowired
    public ScheduledTasks(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    @Scheduled(fixedRate = 60_000)
    @Override
    public void reportCurrentTime() {
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

