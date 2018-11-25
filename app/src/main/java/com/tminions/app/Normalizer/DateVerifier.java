package com.tminions.app.Normalizer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateVerifier implements Verifier{

    static final String columnHeaderRegex = "(YYYY-MM-DD)";
    
    @Override
    public String verify(String cellString) throws InvalidFormatException {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	try {
    	    LocalDate date = formatter.parse(cellString, LocalDate::from);
    	} catch (DateTimeParseException e) {
    		//case where user enters date with no dashes but spaces
    		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy MM dd");
    		try {
    			LocalDate date2 = form.parse(cellString, LocalDate::from);
    			//change from form with spaces to dashes
    			cellString = date2.format(formatter);
    		} catch (DateTimeParseException f) {
    			throw new InvalidFormatException();
    		}
    	}
        return cellString;
    }

    @Override
    public String getColumnHeader() {
        return columnHeaderRegex;
    }
}
