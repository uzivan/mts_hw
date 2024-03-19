package ru.mts.scheduled;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.mts.config.ConfigApp;
import ru.mts.domain.animals.Animal;
import ru.mts.repositories.AnimalsRepository;
import ru.mts.services.CreateAnimalService;
import ru.mts.utils.exceptions.IncorrectYearsException;
import ru.mts.utils.exceptions.InvalidAnimalsSizeException;
import ru.mts.utils.exceptions.NullAnimalFieldException;

@Component(ScheduledTasksMBean.NAME)
public class ScheduledTasks implements ScheduledTasksMBean {

    public static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final AnimalsRepository animalsRepository;
    private final CreateAnimalService createAnimalService;

    @Autowired
    public ScheduledTasks(AnimalsRepository animalsRepository, CreateAnimalService createAnimalService) {
        this.animalsRepository = animalsRepository;
        this.createAnimalService = createAnimalService;
    }

    @Scheduled(fixedRate = 60_000)
    @Override
    public void reportCurrentTime() {
        try {
            log.info("рожденные в високосный год");
            Map<String, LocalDate> animalsFunc1 = animalsRepository.findLeapYearNames();
            log.info(StringUtils.join(animalsFunc1, "\n"));

            log.info("животные которые старше 1 года");
            Map<Animal, Integer> animalsFunc2 = animalsRepository.findOlderAnimal(1);
            log.info(StringUtils.join(animalsFunc2, "\n"));

            log.info("дубликаты");
            Map<String, List<Animal>> animalsFunc3 = animalsRepository.findDuplicate();
            log.info(StringUtils.join(animalsFunc3, "\n"));

            log.info("список котов");
            List<Animal> animals = createAnimalService.createAnimals(10).get("Cat");
            log.info(StringUtils.join(animals, "\n"));

            log.info("средний возраст");
            animalsRepository.findAverageAge(animals);

            log.info("животные, возраст которых больше 5 лет и стоимость больше средней");
            List<Animal> animalsFunc5 = animalsRepository.findOldAndExpensive(animals);
            log.info(StringUtils.join(animalsFunc5, "\n"));

            log.info("3 животных с самой низкой ценой отсортированные в алфавитном порядке");
            List<Animal> animalsFunc6 = null;
            animalsFunc6 = animalsRepository.findMinConstAnimal(animals);
            log.info(StringUtils.join(animalsFunc6, "\n"));
        }catch (NullAnimalFieldException | InvalidAnimalsSizeException | IncorrectYearsException e){
            System.out.println(e.getMessage());
        }
    }

}

