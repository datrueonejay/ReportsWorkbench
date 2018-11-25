package com.tminions.app.screenController;


import com.tminions.app.charts.ChartUtils;
import com.tminions.app.charts.GenerateBarChartReport;
import com.tminions.app.charts.GeneratePieChartReport;
import com.tminions.app.controllers.GenerateReportsController;
import com.tminions.app.controllers.TemplateController;
import com.tminions.app.jsonMaker.JsonMaker;
import com.tminions.app.models.ReportDataModel;
import com.tminions.app.models.TemplateColumnsModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomReportScreenController {

    @FXML private ComboBox<String> selectTemplate;
    @FXML private ComboBox<String> selectColumn;
    private String templateType;
    private String columnType;
    private TemplateColumnsModel currrentColumns;
    private final String reportFolderPath = "";


    public void initialize()
    {
        // Get templates from wherever they are stored
        selectTemplate.setItems(getTemplates());
        // Set up listener for when a template type is selected
        selectTemplate.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue value, String oldSelection, String newSelection) {
                templateType = newSelection;
                currrentColumns = TemplateController.getTemplateColumns(templateType);
                selectColumn.setItems(getColumns());
            }
        });
        
        selectColumn.valueProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue value, String oldSelection, String newSelection) {
                columnType = newSelection;
            }
        });

    }

    private ObservableList<String> getTemplates() {
        List<String> TemplateNames = TemplateController.getAllTemplateNames();
        return FXCollections.observableArrayList(TemplateNames);
    }
    
    private ObservableList<String> getColumns() {
        return FXCollections.observableArrayList(currrentColumns.getColumnNames());
    }

    public void selectBarChart()
    {
        String[] columns = {currrentColumns.getKeyForColumnValue(columnType)};
        String serverResponse = GenerateReportsController.getReportData(columns, templateType);
        ReportDataModel rdm = JsonMaker.convertJsonResponseToRDM(serverResponse);

        HashMap<String, String[][]> reportData = rdm.getReportData();
        String[][] columnData = reportData.get(columns[0]);
        
        // Create unique names for files
        String timeStamp = getDateTimeStamp();
        String barChartFileName = reportFolderPath + String.format("barchart_%s.png", timeStamp);
        String reportFilePath = String.format("%s_%s_%s.pdf", templateType, columnType, timeStamp);
        
        new GenerateBarChartReport(columnData, columnType, "Amount", "Columns", barChartFileName);
        ArrayList<String> chart = new ArrayList<String>();
        chart.add(barChartFileName);

        ChartUtils.generateReport(reportFilePath, templateType, chart);
    }
    
    public void selectPieChart() {
        String[] columns = {currrentColumns.getKeyForColumnValue(columnType)};
        String serverResponse = GenerateReportsController.getReportData(columns, templateType);
        ReportDataModel rdm = JsonMaker.convertJsonResponseToRDM(serverResponse);
        HashMap<String, String[][]> reportData = rdm.getReportData();
        String[][] columnData = reportData.get(columns[0]);
    	
        // Create unique names for files
        String timeStamp = getDateTimeStamp();
        String pieChartFileName = reportFolderPath + String.format("piechart_%s.png", timeStamp);
        String reportFilePath = String.format("%s_%s_%s.pdf", templateType, columnType, timeStamp);
        
        new GeneratePieChartReport(columnData, columnType, pieChartFileName);
        
        ArrayList<String> chart = new ArrayList<String>();
        chart.add(pieChartFileName);

        ChartUtils.generateReport(reportFilePath, templateType, chart);
    }
    
    private String getDateTimeStamp() {
        LocalDateTime currTime = LocalDateTime.now();
        // Parse time into a timestamp that can be used as part of a file name
        return currTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
    }

}
