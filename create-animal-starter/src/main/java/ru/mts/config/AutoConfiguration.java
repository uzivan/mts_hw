package ru.mts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.util.ReflectionUtils;
import ru.mts.bpp.AnimalTypePostBeanProcessor;
import ru.mts.enums.animals.types.PetType;
import ru.mts.enums.animals.types.PredatorType;
import ru.mts.factory.BaseAnimalFactory;
import ru.mts.factory.animalstypes.BasePetFactory;
import ru.mts.factory.animalstypes.BasePredatorFactory;
import ru.mts.services.CreateAnimalService;
import ru.mts.services.CreateAnimalServiceImpl;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(AnimalConfigurationProperties.class)
public class AutoConfiguration {
    private final AnimalConfigurationProperties animalConfigurationProperties;

    @Autowired
    public AutoConfiguration(AnimalConfigurationProperties animalConfigurationProperties) {
        this.animalConfigurationProperties = animalConfigurationProperties;
    }

    @Bean
    public BasePetFactory basePetFactory() {
        Map<PetType, List<String>> namesMap = Arrays.stream(PetType.values())
                .collect(Collectors.toUnmodifiableMap(
                        Function.identity(),
                        petType -> findNamesFromProperties(petType.getTitle())
                ));

        return new BasePetFactory(namesMap);
    }

    @Bean
    public BasePredatorFactory basePredatorFactory() {
        Map<PredatorType, List<String>> namesMap = Arrays.stream(PredatorType.values())
                .collect(Collectors.toUnmodifiableMap(
                        Function.identity(),
                        petType -> findNamesFromProperties(petType.getTitle())
                ));

        return new BasePredatorFactory(namesMap);
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

    private List<String> findNamesFromProperties(String title) {
        Field field = ReflectionUtils.findField(
                AnimalConfigurationProperties.class,
                String.format("%sNames", title),
                List.class
        );

        if (Objects.nonNull(field)) {
            ReflectionUtils.makeAccessible(field);
            try {
                //System.out.println(field.get(animalConfigurationProperties));
                //noinspection unchecked cast
                return (List<String>) field.get(animalConfigurationProperties);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

        return Collections.emptyList();
    }

}