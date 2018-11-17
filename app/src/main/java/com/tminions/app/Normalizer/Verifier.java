package com.tminions.app.Normalizer;

/**
 * The PHONE #, DATE and POSTAL CODE verifier all implement this class.
 *
 */
public interface Verifier {
    /**
     * will take the cell string and check if it's formatted correctly.
     * - If Yes, then it'll return the string.
     * - If its not formatted correctly, it'll try to fix the format:
     *      - if it can fix the format then return the correctly formatted string
     *      - if it can't then throw the invalidFormatException
     *
     * @param cellString the cell's raw string
     * @return the correctly formatted cell string
     * @throws InvalidFormatException if cell's contents are unfixable
     */
    public String verify(String cellString) throws InvalidFormatException;

    /**
     * returns a regex that will match the column header of the cell type that
     * the verifier will validate.
     * @return a regex ^
     */
    public String getColumnHeader();
}
