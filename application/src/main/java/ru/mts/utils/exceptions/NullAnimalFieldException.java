package ru.mts.utils.exceptions;

public class NullAnimalFieldException extends IllegalArgumentException{

    public NullAnimalFieldException(String field){
        super(field + " should not be null");
    }

}
