package com.tminions.app.Normalizer;

public class InvalidFormatException extends Exception{

    /**
     * throw this when the data verifier cannot automatically fix the string
     */
    public InvalidFormatException() {
        super ("Invalid Format");
    }
}
