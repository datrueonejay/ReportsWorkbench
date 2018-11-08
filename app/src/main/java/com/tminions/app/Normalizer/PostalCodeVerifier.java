package com.tminions.app.Normalizer;

import java.util.regex.*;

public class PostalCodeVerifier implements Verifier{

    static final String columnHeaderRegex = "postal code";

    @Override
    public String verify(String cellString) throws InvalidFormatException {
    	//if postal code is given as xxx.ppp where '.' is any char or space
    	//it will correct it to xxxppp
    	String regex = "^(?!.*[DFIOQU])([A-VXY][0-9][A-Z])-? ?.?([0-9][A-Z][0-9])$";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(cellString);
    	if(matcher.matches()) {
    		cellString = (matcher.replaceFirst("$1$2"));
    	}
    	else
    		throw new InvalidFormatException();
        return cellString;
    }

    @Override
    public String getColumnHeader() {
        return columnHeaderRegex;
    }
}
