package com.tminions.app.screenController;


import com.tminions.app.Utils.AlertBox;
import com.tminions.app.charts.ChartUtils;
import com.tminions.app.charts.GenerateBarChartReport;
import com.tminions.app.charts.GeneratePieChartReport;
import com.tminions.app.controllers.GenerateReportsController;
import com.tminions.app.jsonMaker.JsonMaker;
import com.tminions.app.models.ReportDataModel;
import com.tminions.app.pdfMaker.PdfMaker;

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
        ChartUtils.generateReport(reportFilePath, "Language Report", Arrays.asList(barChartFileName, pieChartFileName));

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

        ChartUtils.generateReport(reportFilePath, "Place Services Were Received", Arrays.asList(pieChartFileName, barChartFileName));

    }

    /**
     * Generates a report about population distribution
     */
    public void selectReport3() {
        String[] columns = {"Needs & Assessment - Population Age Distribution", "Employment Related Services - Population Age Distribution",
                "Language Training - Population Age Distribution", "Needs childminding by Service"};
        ReportDataModel rdm = GenerateReportsController.getPopulationReportData();
        HashMap<String, String[][]> reportData = rdm.getReportData();

        String[][] needsPopulation = reportData.get(columns[0]);
        String[][] employmentPopulation = reportData.get(columns[1]);
        String[][] languagePopulation = reportData.get(columns[2]);
        String[][] needsChildminding = reportData.get(columns[3]);


        // Create unique names for files
        String timeStamp = getDateTimeStamp();
        String barChartFileName = reportFolderPath + String.format("barchart_%s.png", timeStamp);
        String pieChartFileName = reportFolderPath + String.format("piechart_%s.png", timeStamp);
        String needsFileName = "needsPopulation"+pieChartFileName;
        String employmentFileName = "employmentPopulation"+pieChartFileName;
        String languageFileName = "languagePopulation"+pieChartFileName;
        String childMindingFileName = "needsChildMinding"+barChartFileName;

        String reportFilePath = String.format("PopulationAndChildMindingReport_%s.pdf", timeStamp);
        // Create the graphs
        GeneratePieChartReport needsChart = new GeneratePieChartReport(needsPopulation, columns[0],needsFileName);
        GeneratePieChartReport employmentChart = new GeneratePieChartReport(employmentPopulation, columns[1], employmentFileName);
        GeneratePieChartReport languageChart = new GeneratePieChartReport(languagePopulation, columns[2], languageFileName);
        GenerateBarChartReport childMindingChart = new GenerateBarChartReport(needsChildminding, columns[3],
                "Number of people", "Type Of Service", childMindingFileName);
        ChartUtils.generateReport(reportFilePath, "Population And Child Minding Statistics", Arrays.asList(needsFileName, employmentFileName, languageFileName, childMindingFileName));

    }

    private String getDateTimeStamp() {
        LocalDateTime currTime = LocalDateTime.now();
        // Parse time into a timestamp that can be used as part of a file name
        return currTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
    }

}