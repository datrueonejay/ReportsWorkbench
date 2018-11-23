package com.tminions.app.jsonMaker;

import com.tminions.app.clientRecord.ClientRecord;
import com.tminions.app.fileParsers.ExcelParser;
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
    private static File excelFileBadFormats = new File("src/test/resources/iCARE_template_bad_formats.xlsx");
    private static File excelFileMock = new File("src/test/resources/EmploymentMock.xlsx");
    private static String templateType = "Employment Template";
    private static ArrayList<File> files = new ArrayList<>();
    private static final String message = "iCARE_template_bad_formats.xlsx should generate json with invalid clients";

    @BeforeAll
    static void init() {
        files.add(excelFile);

    }

    @Test
    @DisplayName("test bad Formats works")
    void testBadFormats() throws IOException {
        ArrayList<File> f = new ArrayList<>();
        f.add(excelFileBadFormats);
        String actual = JsonMaker.jsonFromFiles(f, templateType);
        //System.out.println(actual);
        assertTrue(actual.contains("invalid_rows\" : "), message);
        assertTrue(actual.contains("valid\" : \"false\""), message);

    }

    @Test
    @DisplayName("test mock Data works")
    void testMock() throws IOException {
        ArrayList<File> f = new ArrayList<>();
        f.add(excelFileMock);
        String actual = JsonMaker.jsonFromFiles(f, templateType);
        System.out.println(actual);

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
