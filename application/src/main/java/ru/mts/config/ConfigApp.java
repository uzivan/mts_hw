package ru.mts.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.mts.repositories.AnimalsRepository;
import ru.mts.repositories.AnimalsRepositoryImpl;
import ru.mts.services.CreateAnimalService;

@Configuration
@Import(AutoConfiguration.class)
public class ConfigApp {

    @Autowired
    private CreateAnimalService createAnimalService;

    @Bean(initMethod = "init")
    public AnimalsRepository animalsRepository(@Autowired ObjectProvider<CreateAnimalService> createAnimalServiceObjectProvider) {
        return new AnimalsRepositoryImpl(createAnimalServiceObjectProvider);
    }

}
