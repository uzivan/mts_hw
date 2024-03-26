package ru.mts.scheduled;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mts.domain.animals.Animal;
import ru.mts.factory.SimpleFactoryFunctions;
import ru.mts.repositories.AnimalsRepository;
import ru.mts.services.CreateAnimalService;
import ru.mts.utils.exceptions.NullAnimalFieldException;

import javax.annotation.PostConstruct;

@Component(ScheduledTasksMBean.NAME)
public class ScheduledTasks implements ScheduledTasksMBean {

    private static ReentrantLock lock = new ReentrantLock();

    public static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final AnimalsRepository animalsRepository;
    private final CreateAnimalService createAnimalService;

    @Autowired
    public ScheduledTasks(AnimalsRepository animalsRepository, CreateAnimalService createAnimalService) {
        this.animalsRepository = animalsRepository;
        this.createAnimalService = createAnimalService;
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("task1Thread");

                lock.lock();

                log.info(Thread.currentThread().getName());
                log.info("дубликаты");
                animalsRepository.printDuplicates();

                lock.unlock();
            }
        }, 0, 10, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("task2Thread");

                lock.lock();

                try{
                    log.info(Thread.currentThread().getName());
                    log.info("средний возраст");
                    List<Animal> animals = createAnimalService.createAnimals(10).get("Cat");
                    animalsRepository.findAverageAge(animals);
                }catch (NullAnimalFieldException e){
                    log.info(e.getMessage());
                }
                finally {
                    lock.unlock();
                }
            }
        }, 0, 20, TimeUnit.SECONDS);
    }

    @Override
    public void reportCurrentTime() {}

}

