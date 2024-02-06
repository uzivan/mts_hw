package ru.mts.hw3.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import ru.mts.hw3.bpp.AnimalTypePostBeanProcessor;
import ru.mts.hw3.enums.animals.AnimalType;
import ru.mts.hw3.factory.BaseAnimalFactory;
import ru.mts.hw3.factory.animalstypes.BasePetFactory;
import ru.mts.hw3.factory.animalstypes.BasePredatorFactory;
import ru.mts.hw3.repositories.AnimalsRepository;
import ru.mts.hw3.repositories.AnimalsRepositoryImpl;
import ru.mts.hw3.services.hw5.CreateAnimalService;
import ru.mts.hw3.services.hw5.CreateAnimalServiceImpl;


@Configurable
public class ConfigurationApp {
    @Bean
    public BasePetFactory basePetFactory() {
        return new BasePetFactory();
    }

    @Bean
    public BasePredatorFactory basePredatorFactory() {
        return new BasePredatorFactory();
    }

    @Bean
    public BaseAnimalFactory baseAnimalFactory(BasePetFactory basePetFactory, BasePredatorFactory basePredatorFactory) {
        return new BaseAnimalFactory(basePetFactory, basePredatorFactory);
    }

    @Bean
    @Scope("prototype")
    public AnimalType animalType() {
        return AnimalType.PET;
    }

    @Bean
    @Scope("prototype")
    public CreateAnimalService createAnimalService(AnimalType animalType, BaseAnimalFactory baseAnimalFactory) {
        return new CreateAnimalServiceImpl(animalType, baseAnimalFactory);
    }

    @Bean
    public AnimalsRepository animalsRepository(ApplicationContext applicationContext) {
        return new AnimalsRepositoryImpl(applicationContext);
    }

    @Bean
    public AnimalTypePostBeanProcessor animalTypePostBeanProcessor() {
        return new AnimalTypePostBeanProcessor();
    }
}
