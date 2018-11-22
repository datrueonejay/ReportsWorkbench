package com.tminions.app;


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

import java.util.HashMap;
import java.util.List;

public class ReportGenerationScreenController {

    @FXML private ComboBox<String> selectTemplate;
    @FXML private ComboBox<String> selectColumn;
    private String templateType;
    private String columnType;
    private TemplateColumnsModel currrentColumns;


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
        System.out.println("Generating Bar Chart for Template: "+ templateType + " , Column: "+ columnType);
        String[] columns = {currrentColumns.getKeyForColumnValue(columnType)};
        String serverResponse = GenerateReportsController.getReportData(columns, templateType);
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
