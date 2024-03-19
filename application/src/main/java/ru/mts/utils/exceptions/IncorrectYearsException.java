package ru.mts.utils.exceptions;

public class IncorrectYearsException extends IllegalArgumentException{

    public IncorrectYearsException() {
        super("Years should be > 0");
    }

}
