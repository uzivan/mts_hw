package ru.mts.scheduled;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mts.domain.animals.Animal;
import ru.mts.repositories.AnimalsRepository;

import java.util.Set;

@Component(ScheduledTasksMBean.NAME)
public class ScheduledTasks implements ScheduledTasksMBean {

    public static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final AnimalsRepository animalsRepository;

    @Autowired
    public ScheduledTasks(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    @Scheduled(fixedRate = 60_000)
    @Override
    public void reportCurrentTime() {
        log.info("рожденные в високосный год");
        String[] animalsFunc1 = animalsRepository.findLeapYearNames();
        log.info(StringUtils.join(animalsFunc1, "\n"));

        log.info("животные которые старше 1 года");
        Animal[] animalsFunc2 = animalsRepository.findOlderAnimal(1);
        log.info(StringUtils.join(animalsFunc2, "\n"));

        log.info("дубликаты");
        Set<Animal> animalsFunc3 = animalsRepository.findDuplicate();
        animalsRepository.printDuplicates(animalsFunc3);
    }

}

