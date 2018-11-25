package com.tminions.app.pdfMaker;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfMakerTest {

    static final String fileName0 = "src/test/resources/pdfMakerTestResource/filename0.pdf";
    static final String fileName1 = "src/test/resources/pdfMakerTestResource/filename1.pdf";

    // test making a file and make sure that it's there
    @Test
    @DisplayName("test making a pdf using PdfMaker and adding title, image")
    void testMakePdf() throws IOException{
        PdfMaker p = new PdfMaker(fileName0);
        p.addTitle("Test Title");
        p.addImage("src/test/resources/pdfMakerTestResource/leader.png",50, 50, 500, 500);
        p.saveAndClose();

        File tempFile = new File(fileName0);
        assertTrue(tempFile.exists());
    }

    @AfterAll
    static void finishAndClean() {
        File tempFile = new File(fileName0);
        assertTrue(tempFile.exists());
        tempFile.delete();
    }


}
