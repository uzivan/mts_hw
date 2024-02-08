package ru.mts.hw3.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.mts.hw3.bpp.AnimalTypePostBeanProcessor;
import ru.mts.hw3.factory.BaseAnimalFactory;
import ru.mts.hw3.factory.animalstypes.BasePetFactory;
import ru.mts.hw3.factory.animalstypes.BasePredatorFactory;
import ru.mts.hw3.repositories.AnimalsRepository;
import ru.mts.hw3.repositories.AnimalsRepositoryImpl;
import ru.mts.hw3.services.hw6.CreateAnimalService;
import ru.mts.hw3.services.hw6.CreateAnimalServiceImpl;

@Configuration
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
    public BaseAnimalFactory baseAnimalFactory(@Autowired BasePetFactory basePetFactory,
                                               @Autowired BasePredatorFactory basePredatorFactory) {
        return new BaseAnimalFactory(basePetFactory, basePredatorFactory);
    }

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean(name = CreateAnimalService.NAME)
    public CreateAnimalService createAnimalService(@Autowired BaseAnimalFactory baseAnimalFactory) {
        return new CreateAnimalServiceImpl(baseAnimalFactory);
    }

    @Bean(initMethod = "init")
    public AnimalsRepository animalsRepository(@Autowired ObjectProvider<CreateAnimalService> createAnimalServiceObjectProvider) {
        return new AnimalsRepositoryImpl(createAnimalServiceObjectProvider);
    }

    @Bean
    public AnimalTypePostBeanProcessor animalTypePostBeanProcessor() {
        return new AnimalTypePostBeanProcessor();
    }

}
