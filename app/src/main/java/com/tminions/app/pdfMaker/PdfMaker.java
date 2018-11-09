package com.tminions.app.pdfMaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


public class PdfMaker {

    private String filename;
    private PDDocument doc;
    private PDPage page;
    private PDPageContentStream content;

    /**
     * instantiate when you'd like to make a new PDF document
     * @param filename the name of the file
     * @throws IOException if there's an io exception
     */
    public PdfMaker(String filename) throws IOException{
        this.filename = filename;

        doc = new PDDocument();
        page = new PDPage();

        doc.addPage(page);

        content = new PDPageContentStream(doc, page);

    }

    /**
     * when you'd like to add a title to the page
     * @param title the title you'd like to add
     * @throws IOException if there's an io exception
     */
    public void addTitle(String title) throws IOException{
        content.beginText();
        content.setFont(PDType1Font.TIMES_ROMAN, 26);
        content.newLineAtOffset(220, 750);
        content.showText(title);
        content.endText();
    }

    /**
     * when you'd like to add an image to the page
     * @param pathToImage the image path
     * @param xCoord where to put image, origin (0,0) is bottom left
     * @param yCoord where to put image
     * @param width size of image (page is ~600 wide, 770 tall)
     * @param height size of image
     * @throws IOException
     */
    public void addImage(String pathToImage, int xCoord, int yCoord, int width, int height) throws IOException{
        PDImageXObject image = PDImageXObject.createFromFileByExtension(new File(pathToImage), this.doc);

        content.drawImage(image, xCoord, yCoord, width, height);
    }

    /**
     * when you're done
     */
    public void saveAndClose() throws IOException{
        content.close();
        doc.save(filename);
        doc.close();
    }

    public static void main(String[] args) {

        try{

            System.out.println("Create Simple PDF file with Text");
            String fileName = "PdfWithImage.pdf"; // name of our file

            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();

            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 26);
            content.newLineAtOffset(220, 750);
            content.showText("Registration Form");
            content.endText();


            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 16);
            content.newLineAtOffset(80, 700);
            content.showText("Name : ");
            content.endText();


            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 16);
            content.newLineAtOffset(80,650);
            content.showText("Father Name : ");
            content.endText();

            content.beginText();
            content.newLineAtOffset(80,600);
            content.showText("DOB : ");
            content.endText();

            PDImageXObject image = PDImageXObject.createFromFileByExtension(new File("/Users/gary/Documents/CSCC01/leader.png"), doc);

            content.drawImage(image,0,0, 600, 750);


            content.close();
            doc.save(fileName);
            doc.close();

            System.out.println("your file created in : "+ System.getProperty("user.dir"));

        }
        catch(IOException e){

            System.out.println(e.getMessage());

        }

    }
}
