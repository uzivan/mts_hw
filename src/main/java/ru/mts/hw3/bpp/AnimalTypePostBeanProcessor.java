package ru.mts.hw3.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.mts.hw3.enums.animals.AnimalType;

import java.util.Random;

public class AnimalTypePostBeanProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AnimalType) {
            Random random = new Random();
            AnimalType[] animalTypes = AnimalType.values();
            int num = random.nextInt(animalTypes.length);
            bean = animalTypes[num];
        }
        return bean;
    }
}
