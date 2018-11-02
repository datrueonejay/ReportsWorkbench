package com.tminions.app.Normalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneVerifier implements Verifier {

    static final String columnHeaderRegex = "phone number";

    @Override
    /** valid numbers
    phoneNumbers("1234567890");
    phoneNumbers("123-456-7890");
    phoneNumbers("123.456.7890");
    phoneNumbers("123 456 7890");
    phoneNumbers("(123) 456 7890");
     
    Invalid phone numbers
    phoneNumbers("12345678");
    phoneNumbers("12-12-111");
    **/
    public String verify(String cellString) throws InvalidFormatException {
    	String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(cellString);
    	if (matcher.matches()) {
    		cellString = (matcher.replaceFirst("$1-$2-$3"));
    	}
    	else {
    		throw new InvalidFormatException();
    	}
        return cellString;
    }

    @Override
    public String getColumnHeader() {
        return columnHeaderRegex;
    }
}
