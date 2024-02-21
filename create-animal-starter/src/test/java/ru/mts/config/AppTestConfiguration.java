package ru.mts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ReflectionUtils;
import ru.mts.enums.animals.AnimalType;
import ru.mts.enums.animals.types.PetType;
import ru.mts.enums.animals.types.PredatorType;
import ru.mts.factory.BaseAnimalFactory;
import ru.mts.factory.animalstypes.BasePetFactory;
import ru.mts.factory.animalstypes.BasePredatorFactory;
import ru.mts.services.CreateAnimalService;
import ru.mts.services.CreateAnimalServiceImpl;

@Configuration
public class AppTestConfiguration {

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

    @Bean
    public CreateAnimalService createAnimalService(@Autowired BaseAnimalFactory baseAnimalFactory) {
        return new CreateAnimalServiceImpl(baseAnimalFactory);
    }

}
