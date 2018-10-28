package com.tminions.app.Normalizer;

public class PhoneVerifier implements Verifier {

    static final String columnHeaderRegex = "phone number";

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
