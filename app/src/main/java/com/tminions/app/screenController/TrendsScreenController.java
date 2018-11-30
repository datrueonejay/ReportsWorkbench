package com.tminions.app.screenController;

import com.tminions.app.Utils.AlertBox;
import com.tminions.app.charts.GenerateBarGraphForTrends;
import com.tminions.app.controllers.TemplateController;
import com.tminions.app.controllers.TrendsDataController;
import com.tminions.app.jsonMaker.JsonMaker;
import com.tminions.app.models.TrendReportDataModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;

public class TrendsScreenController
{
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
        return FXCollections.observableArrayList(TemplateController.getAllTemplateNames());
    }


    public void generateTrendReport()
    {
        columnValueSelected = columnSelectField.getText();
        String columnData = TrendsDataController.getAllTemplateData(templateType);
        TrendReportDataModel trdm = JsonMaker.generateTrendDataFromJSON(columnData, trendReportType, columnValueSelected, templateType);
        //String str1 = "computer training";
        //String str2 = "training_received_computer_ind";

        //System.out.println("The distance is: " + getNumberOfSubstrings(str1, str2, " ", "_"));


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