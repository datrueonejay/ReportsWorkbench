package com.tminions.app.Normalizer;

public class PostalCodeVerifier implements Verifier{

    static final String columnHeaderRegex = "postal code";

    @Override
    public String verify(String cellString) throws InvalidFormatException {
        return cellString;
        //TODO: @Balaji implement
    }

    @Override
    public String getColumnHeader() {
        return columnHeaderRegex;
    }
}
