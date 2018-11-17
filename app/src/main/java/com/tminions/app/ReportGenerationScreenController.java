package com.tminions.app;


import com.tminions.app.charts.GenerateBarChartReport;
import com.tminions.app.charts.GeneratePieChartReport;
import com.tminions.app.controllers.GenerateReportsController;
import com.tminions.app.jsonMaker.JsonMaker;
import com.tminions.app.models.LoginModel;
import com.tminions.app.models.ReportDataModel;
import com.tminions.app.pdfMaker.PdfMaker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ReportGenerationScreenController {

    private final String reportOneTemplate = "Information and Orientation";
    private final String reportTwoTemplate = "Employment Services";

    private final String reportFolderPath = "";

    /**
     * Generates a report for languages
     */
    public void selectReport()
    {
        String[] columns = {"service_language_id"};
        String serverResponse = GenerateReportsController.getReportData(columns, reportOneTemplate);
        ReportDataModel rdm = JsonMaker.convertJsonResponseToRDM(serverResponse);

        HashMap<String, String[][]> reportData = rdm.getReportData();
        String[][] columnData = reportData.get(columns[0]);

        // Create unique names for files
        String timeStamp = getDateTimeStamp();
        String barChartFileName = reportFolderPath + String.format("barchart_%s.png", timeStamp);
        String pieChartFileName = reportFolderPath + String.format("piechart_%s.png", timeStamp);
        String reportFilePath = String.format("LanguageReport_%s.pdf", timeStamp);
        // Create two graphs
        GenerateBarChartReport chart1 = new GenerateBarChartReport(columnData, "Language Report",
                                                                           "Occurrences",
                                                                         "Languages", barChartFileName);
        GeneratePieChartReport chart2 = new GeneratePieChartReport(columnData, "Language Distribution",
                pieChartFileName);
        // Create pdf report
        generateReport(reportFilePath, "Language Report", Arrays.asList(barChartFileName, pieChartFileName));

    }

    /**
     * Generates a report about where client received services
     */
    public void selectReport2() {
    	String[] columns = {"institution_type_id"};
    	String serverResponse = GenerateReportsController.getReportData(columns, reportTwoTemplate);
        ReportDataModel rdm = JsonMaker.convertJsonResponseToRDM(serverResponse);

        HashMap<String, String[][]> reportData = rdm.getReportData();
        String[][] columnData = reportData.get(columns[0]);

        // Create unique names for files
        String timeStamp = getDateTimeStamp();
        String barChartFileName = reportFolderPath + String.format("barchart_%s.png", timeStamp);
        String pieChartFileName = reportFolderPath + String.format("piechart_%s.png", timeStamp);
        String reportFilePath = String.format("PlaceOfServicesReport_%s.pdf", timeStamp);
        // Create two graphs
        GenerateBarChartReport chart1 = new GenerateBarChartReport(columnData, "Where Services Were Received",
                "Occurrences",
                "Place", barChartFileName);
        GeneratePieChartReport chart2 = new GeneratePieChartReport(columnData, "Where Services Were Received Distribution",
                pieChartFileName);

        generateReport(reportFilePath, "Place Services Were Received", Arrays.asList(pieChartFileName, barChartFileName));

    }

    private void generateReport(String filePath, String reportTitle, List<String> chartPaths) {
        // Instantiate pdf maker
        try {
            PdfMaker pdfMaker = new PdfMaker(filePath);

            pdfMaker.addTitle(reportTitle);
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

            pdfMaker.saveAndClose();
            AlertBox.display("Report Created!", String.format("File is at %s\\%s", System.getProperty("user.dir"), filePath));
        } catch (IOException e) {
            e.printStackTrace();
            AlertBox.display("Report Creation Failed!", "An error occurred trying to create the report.");
        }
    }

    private String getDateTimeStamp() {
        LocalDateTime currTime = LocalDateTime.now();
        // Parse time into a timestamp that can be used as part of a file name
        return currTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

}
