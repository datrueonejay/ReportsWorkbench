package com.tminions.app.charts;

import com.tminions.app.Utils.AlertBox;
import com.tminions.app.pdfMaker.PdfMaker;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChartUtils
{
    public static void createPNGfromChart(JFreeChart chart, String fileName, int height, int width)
    {
        try {
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File(fileName);
            ChartUtilities.saveChartAsPNG(file1, chart, width, height, info);
        }
        catch (IOException ioException)
        {
            AlertBox.display("Char Creation Failed!", "An error occurred trying to create a chart.");
        }
    }

    public static void generateReport(String filePath, String reportTitle, List<String> chartPaths) {
        // Instantiate pdf maker
        try {
            PdfMaker pdfMaker = new PdfMaker(filePath);

            pdfMaker.addTitle(reportTitle);
            if (chartPaths.size() == 1) {
                int xCoord = 50;
                int yCoord = 50;
                // Loop through and add graphs to the pdf
                // This currently fits 4 graphs using this method
                for (String chartPath : chartPaths) {
                    pdfMaker.addImage(chartPath, xCoord, yCoord, 500, 500);
                    // Move left if we have space
                    if (xCoord == 50) {
                        xCoord = 300;
                        // Otherwise move to next row
                    } else {
                        yCoord -= 300;
                        xCoord = 50;
                    }
                }
            } else {
                int xCoord = 50;
                int yCoord = 350;
                // Loop through and add graphs to the pdf
                // This currently fits 4 graphs using this method
                for (String chartPath : chartPaths) {
                    pdfMaker.addImage(chartPath, xCoord, yCoord, 250, 250);
                    // Move left if we have space
                    if (xCoord == 50) {
                        xCoord = 300;
                        // Otherwise move to next row
                    } else {
                        yCoord -= 300;
                        xCoord = 50;
                    }
                }
            }


            pdfMaker.saveAndClose();
            AlertBox.display("Report Created!", String.format("File is at %s\\%s", System.getProperty("user.dir"), filePath));
        } catch (IOException e) {
            AlertBox.display("Report Creation Failed!", "An error occurred trying to create the report.");
        }
    }
}
