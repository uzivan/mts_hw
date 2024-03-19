package ru.mts.utils.exceptions;

import java.io.IOException;

public class InvalidAnimalsSizeException extends IOException {

    public InvalidAnimalsSizeException() {
        super("Animal list should be more 3");
    }

}