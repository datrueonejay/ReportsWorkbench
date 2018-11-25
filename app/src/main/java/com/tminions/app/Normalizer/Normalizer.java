package com.tminions.app.Normalizer;

import java.util.HashMap;

/**
 * class that handles where to send the string given the column name
 *
 */
public class Normalizer {

    // Holds the list of columnHeaders that need to be verified
    static HashMap<String, Verifier> columnHeaderToVerifier = new HashMap<>();

    /**
     * adds a verifier to the <tt>columnHeaderToVerifier</tt> hashmap so
     * that we know which verifier to use for which column header
     * @param verifier the verifier we're registering
     */
    public static void register(Verifier verifier){
        columnHeaderToVerifier.put(verifier.getColumnHeader(), verifier);

    }

    /**
     * - If its a column that requires a specifically formatted string,
     *    send the string to the proper verifier
     * - Otherwise just return the String
     * @param cellString the raw String from the cell.
     * @param columnHead the column head that the cellString was found under
     * @return the properly formatted String
     */
    public static String verify(String cellString, String columnHead)
            throws InvalidFormatException {
        // call whichever key is contained in columnHead
        for (String key: columnHeaderToVerifier.keySet()){
            if (columnHead.toLowerCase().contains(key.toLowerCase())) {
                return columnHeaderToVerifier.get(key).verify(cellString);
            }
        }

        // if its not a formatted-required string just return it
        return cellString;
    }

}
