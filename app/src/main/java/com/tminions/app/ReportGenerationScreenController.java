package com.tminions.app;


import com.tminions.app.charts.GenerateBarChartReport;
import com.tminions.app.charts.GeneratePieChartReport;
import com.tminions.app.controllers.GenerateReportsController;
import com.tminions.app.jsonMaker.JsonMaker;
import com.tminions.app.models.LoginModel;
import com.tminions.app.models.ReportDataModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.util.HashMap;

public class ReportGenerationScreenController {

    private static String defaulTemplate = "Employment Services";
    @FXML private ComboBox<String> selectTemplate;
    @FXML private ComboBox<String> selectColumn;
    private String templateType;
    private String columnType;


    public void initialize()
    {
        // Get templates from wherever they are stored
        selectTemplate.setItems(getTemplates());
        // Set up listener for when a template type is selected
        selectTemplate.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue value, String oldSelection, String newSelection) {
                templateType = newSelection;
            }
        });

    }

    private ObservableList<String> getTemplates() {
        // TODO: Replace with actual code to get templates frm wherever they are stored
        return FXCollections.observableArrayList(
                "Client Profile Bulk Template",
                "Needs Assessment & Referral Service Template",
                "Employment Related Services Template",
                "Community Connections Template",
                "Information & Orientation Template",
                "Client Enrollment Template",
                "Course Setup Template",
                "Client Exit Template"
        );
    }

    public void selectBarChart()
    {
        System.out.println("Selected Language report.");
        String[] columns = {"official_language_id"};
        String serverResponse = GenerateReportsController.getReportData(columns, defaulTemplate);
        ReportDataModel rdm = JsonMaker.convertJsonResponseToRDM(serverResponse);
        System.out.println(rdm.toString());

        HashMap<String, String[][]> reportData = rdm.getReportData();
        String[][] columnData = reportData.get(columns[0]);

//        SwingUtilities.invokeLater(() -> {
            GenerateBarChartReport ex = new GenerateBarChartReport(columnData, "Language Report",
                                                                               "Occurrences",
                                                                             "Languages");
//            ex.setVisible(true);
//        });
//
//        SwingUtilities.invokeLater(() -> {
            GeneratePieChartReport gpcr = new GeneratePieChartReport(columnData, "Language Distribution");
//            gpcr.setVisible(true);
//        });
    }
    
    public void selectPieChart() {
    	System.out.println("Selected type of Institution/Organization Where Client Received Services.");
    	String[] columns = {"Type of Institution/Organization Where Client Received Services"};
    	String serverResponse = GenerateReportsController.getReportData(columns, defaulTemplate);
    }

}
