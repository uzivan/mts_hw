package ru.mts.utils;

import ru.mts.utils.exceptions.IncorrectYearsException;
import ru.mts.utils.exceptions.InvalidAnimalsSizeException;
import ru.mts.utils.exceptions.NullAnimalFieldException;


public final class Preconditions {

    private Preconditions() {
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void checkYears(boolean expression) {
        if (!expression) {
            throw new IncorrectYearsException();
        }
    }

    public static void checkAnimalField(boolean expression, Object nullField) {
        if (!expression) {
            throw new NullAnimalFieldException(String.valueOf(nullField));
        }
    }

    public static void checkAnimalList(boolean expression) throws InvalidAnimalsSizeException {
        if (!expression) {
            throw new InvalidAnimalsSizeException();
        }
    }

}