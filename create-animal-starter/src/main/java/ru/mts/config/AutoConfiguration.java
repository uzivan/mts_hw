package ru.mts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ru.mts.bpp.AnimalTypePostBeanProcessor;
import ru.mts.enums.animals.types.PetType;
import ru.mts.enums.animals.types.PredatorType;
import ru.mts.factory.BaseAnimalFactory;
import ru.mts.factory.animalstypes.BasePetFactory;
import ru.mts.factory.animalstypes.BasePredatorFactory;
import ru.mts.services.CreateAnimalService;
import ru.mts.services.CreateAnimalServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class AutoConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public BasePetFactory basePetFactory() {
        BasePetFactory basePetFactory = new BasePetFactory();

        Map<PetType, List<String>> namesMap = new HashMap<>();

        for (PetType petType : PetType.values()) {
            List<String> names = getNamesFromProperties(petType.getTitle());

            namesMap.put(petType, names);
        }

        basePetFactory.setNames(namesMap);

        return basePetFactory;
    }

    @Bean
    public BasePredatorFactory basePredatorFactory() {
        BasePredatorFactory basePredatorFactory = new BasePredatorFactory();

        Map<PredatorType, List<String>> namesMap = new HashMap<>();

        for (PredatorType predatorType : PredatorType.values()) {
            List<String> names = getNamesFromProperties(predatorType.getTitle());

            namesMap.put(predatorType, names);
        }

        basePredatorFactory.setNames(namesMap);

        return basePredatorFactory;
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

    @Bean
    public AnimalTypePostBeanProcessor animalTypePostBeanProcessor() {
        return new AnimalTypePostBeanProcessor();
    }

    private List<String> getNamesFromProperties(String title) {
        final String prefix = "application.animal.";
        final String suffix = ".name";

        String path = prefix + title + suffix;

        String allNames = environment.getProperty(path);

        return List.of(allNames.split(","));
    }

}
