package com.tminions.app.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DateVerifier implements Verifier{

    static final String columnHeaderRegex = "(YYYY-MM-DD)";
    
    @Override
    public String verify(String cellString) throws InvalidFormatException {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	try {
    	    LocalDate date = formatter.parse(cellString, LocalDate::from);
    	} catch (DateTimeParseException e) {
    	    throw new InvalidFormatException();
    	}
        return cellString;
    }

    @Override
    public String getColumnHeader() {
        return columnHeaderRegex;
    }
}
