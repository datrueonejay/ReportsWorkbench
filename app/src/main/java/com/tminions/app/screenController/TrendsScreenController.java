package com.tminions.app.screenController;

import com.tminions.app.Utils.AlertBox;
import com.tminions.app.charts.GenerateBarGraphForTrends;
import com.tminions.app.controllers.TrendsDataController;
import com.tminions.app.jsonMaker.JsonMaker;
import com.tminions.app.models.TrendReportDataModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.io.File;

public class TrendsScreenController
{
    @FXML private GridPane root;
    @FXML private ListView<File>  filesToUploadUi;
    @FXML private Button generateTrendReportButton;
    @FXML private ComboBox<String> selectTemplateType;
    @FXML private ComboBox<String> selectTrendType;
    @FXML private TextField columnSelectField;


    private String columnValueSelected;
    private String templateType;
    private String trendReportType;

    public void initialize()
    {
        // Get templates from wherever they may be stored.
        selectTemplateType.setItems(getTemplates());
        // Set up listener for when a template type is selected
        selectTemplateType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 templateType = newValue;
            }
        });

        // Get the get trend types that can be generated.
        selectTrendType.setItems(getTrendTypes());
        // Set up the listener for the trend type drop down box.
        selectTrendType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                trendReportType = newValue;
            }
        });




    }


    private ObservableList<String> getTrendTypes() {
        // TODO: Replace with actual code to get templates frm wherever they are stored
        return FXCollections.observableArrayList(
                "By Age",
                 "By Month"
        );
    }

    private ObservableList<String> getTemplates() {
        // TODO: Replace with actual code to get templates frm wherever they are stored
        return FXCollections.observableArrayList(
                  "Client Profile",
                "Needs Assessment & Referrals",
                "Community Connections",
                "Info and Orientation",
                "Employment Services",
                "LT Client Enrol",
                "LT Course Setup",
                "LT Client Exit"
        );
    }


    public void generateTrendReport()
    {
        columnValueSelected = columnSelectField.getText();
        String columnData = TrendsDataController.getAllTemplateData(templateType);

        TrendReportDataModel trdm = JsonMaker.generateTrendDataFromJSON(columnData, trendReportType, columnValueSelected, templateType);

        SwingUtilities.invokeLater(() -> {
            GenerateBarGraphForTrends ex = new GenerateBarGraphForTrends(trdm.getTrendReportData(),
                    "Report for [" + trdm.getClosestColumn()+ "] " + trendReportType,
                    "Number of Users",
                     trendReportType,
                     trdm.getClosestColumn());
        });
        AlertBox.display("Report Created!", String.format("File is at %s\\%s", System.getProperty("user.dir"), "Trend Report For " + trdm.getClosestColumn() + ".pdf"));
    }
}