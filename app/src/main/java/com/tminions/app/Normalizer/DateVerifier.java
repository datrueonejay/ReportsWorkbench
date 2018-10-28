package com.tminions.app.Normalizer;

public class DateVerifier implements Verifier{

    static final String columnHeaderRegex = "(YYYY-MM-DD)";

    @Override
    public String verify(String cellString) {
        return cellString;
        //TODO: @Balaji implement
    }

    @Override
    public String getColumnHeader() {
        return columnHeaderRegex;
    }
}
