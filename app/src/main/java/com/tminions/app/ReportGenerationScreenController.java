package com.tminions.app;


import com.tminions.app.charts.GenerateBarChartReport;
import com.tminions.app.charts.GeneratePieChartReport;
import com.tminions.app.controllers.GenerateReportsController;
import com.tminions.app.jsonMaker.JsonMaker;
import com.tminions.app.models.LoginModel;
import com.tminions.app.models.ReportDataModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.swing.*;
import java.util.HashMap;

public class ReportGenerationScreenController {

    private static String defaulTemplate = "Employment Services";
    @FXML private Button selectReport1;
    @FXML private Button selectReport2;

    public void initialize()
    {

    }


    public void selectReport()
    {
        System.out.println("Selected Language report.");
        String[] columns = {"official_language_id"};
        String serverResponse = GenerateReportsController.getReportData(columns, defaulTemplate);
        ReportDataModel rdm = JsonMaker.convertJsonResponseToRDM(serverResponse);
        System.out.println(rdm.toString());

        HashMap<String, String[][]> reportData = rdm.getReportData();
        String[][] columnData = reportData.get(columns[0]);

        SwingUtilities.invokeLater(() -> {
            GenerateBarChartReport ex = new GenerateBarChartReport(columnData, "Language Report",
                                                                               "Occurrences",
                                                                             "Languages");
            ex.setVisible(true);
        });

        SwingUtilities.invokeLater(() -> {
            GeneratePieChartReport gpcr = new GeneratePieChartReport(columnData, "Language Distribution");
            gpcr.setVisible(true);
        });
    }
    
    public void selectReport2() {
    	System.out.println("Selected type of Institution/Organization Where Client Received Services.");
    	String[] columns = {"Type of Institution/Organization Where Client Received Services"};
    	String serverResponse = GenerateReportsController.getReportData(columns, defaulTemplate);
    }

}
