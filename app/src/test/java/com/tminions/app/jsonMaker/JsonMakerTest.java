package com.tminions.app.jsonMaker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonMakerTest {

    private static File excelFile = new File("src/test/resources/iCARE_template.xlsx");
    private static File excelFile2 = new File("src/test/resources/iCARE_template2.xlsx");
    private static String templateType = "Employment Template";
    private static ArrayList<File> files = new ArrayList<>();

    @BeforeAll
    static void init() {
        files.add(excelFile);
    }

    @Test
    @DisplayName("test make json one file")
    void makeJsonOneFileTest() throws IOException{
        String actual = JsonMaker.jsonFromFiles(files, templateType);
        assertTrue(actual.contains("\"Employment Template\" : {"));

        //TODO: implement test
    }

    @Test
    @DisplayName("test make json two files")
    void makeJsonTwoFileTest() throws IOException{
        files.add(excelFile2);
        String actual = JsonMaker.jsonFromFiles(files, templateType);
        assertTrue(actual.contains(",\n\t\"blahblah2\" : {"));
        //TODO: implement test
    }

    //TODO: add more tests

}
