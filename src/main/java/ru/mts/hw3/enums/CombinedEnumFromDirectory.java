package ru.mts.hw3.enums;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CombinedEnumFromDirectory {
    public enum CombinedEnum {
        // Elements will be added dynamically
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String packageName = "animals.types"; // Замените на имя вашего пакета

        List<Class> enumClasses = getEnumClasses(packageName);
        for (Class enumClass : enumClasses) {
            Field[] fields = enumClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().isAssignableFrom(enumClass)) {
                    try {
                        CombinedEnum.valueOf(field.getName());
                    } catch (IllegalArgumentException e) {
                        CombinedEnum.valueOf(field.getName());
                    }
                }
            }
        }

        CombinedEnum[] els = CombinedEnum.values();

        System.out.println(els.length);

        for (CombinedEnum element : CombinedEnum.values()) {
            System.out.println(element);
        }
    }

    private static List<Class> getEnumClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        System.out.println(path);
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            if (directory.exists()) {
                String[] files = directory.list();
                for (String file : files) {
                    if (file.endsWith(".class")) {
                        String className = packageName + '.' + file.substring(0, file.length() - 6);
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isEnum()) {
                            classes.add(clazz);
                        }
                    }
                }
            }
        }
        return classes;
    }
}