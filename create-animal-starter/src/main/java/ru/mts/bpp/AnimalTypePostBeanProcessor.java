package ru.mts.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import ru.mts.enums.animals.AnimalType;
import ru.mts.services.CreateAnimalService;


import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Random;


public class AnimalTypePostBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (CreateAnimalService.NAME.equals(beanName)
                || bean instanceof CreateAnimalService) {

            Field field = ReflectionUtils.findField(bean.getClass(), "animalType", AnimalType.class);
            if (Objects.isNull(field)) {
                throw new RuntimeException("Caramba, reflection not help");
            }

            ReflectionUtils.makeAccessible(field);

            Random random = new Random();
            AnimalType[] animalTypes = AnimalType.values();
            int num = random.nextInt(animalTypes.length);

            ReflectionUtils.setField(field, bean, animalTypes[num]);
        }

        return bean;
    }

}

