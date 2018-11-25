package com.tminions.app.screenController;

import com.tminions.app.Utils.AlertBox;
import com.tminions.app.Utils.SceneController;
import com.tminions.app.controllers.DataUploadController;
import com.tminions.app.controllers.TemplateController;
import com.tminions.app.jsonMaker.JsonMaker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.ListView;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class DataUploadScreenController {

    private ObservableList filesToUpload = FXCollections.observableArrayList();
    private String templateType;

    @FXML private GridPane root;
    @FXML private ListView<File> filesToUploadUi;
    @FXML private Button uploadButton;
    @FXML private ComboBox<String> templateTypesUi;

    public void initialize() {
        // Get templates from wherever they are stored
        templateTypesUi.setItems(getTemplates());
        // Set up listener for when a template type is selected
        templateTypesUi.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue value, String oldSelection, String newSelection) {
                templateType = newSelection;
                updateUploadButton();
            }
        });
    }

    public void selectFiles() {
        // Set up file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add iCare Files To Submit");
        fileChooser.getExtensionFilters().addAll(
        		new ExtensionFilter("Excel Files .xlsx", "*.xlsx"),
                new ExtensionFilter("Excel Files (97 - 2003) .xls", "*.xls"),
                new ExtensionFilter("All Files", "*.*"));
        // Let user pick files
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());
        // Loop through files if at least one file is selected
        if (selectedFiles != null) {
            // Loop through all files selected
            for (File currFile : selectedFiles) {
                if (!filesToUpload.contains(currFile)) {
                    addFile(currFile);
                }
            }
        }
    }

    public void uploadFiles() {
    	// Get username to include in upload request
    	String username = SceneController.getSceneController().getUsername();
        // Parse Json
        ArrayList<File> files = new ArrayList<File>(filesToUpload);
        try {
            String json = JsonMaker.jsonFromFiles(files, templateType);
            // Send request
            HttpResponse<JsonNode> res = DataUploadController.uploadData(username, json);
            if (res != null && (res.getStatus() == 200)){
                AlertBox.display("Result Successful", "Data Uploaded Successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertBox.display("Error: IOException", e.getMessage());
        }

    }

    public void removeSelectedFile() {
        // Get index of selected file
        int index = filesToUploadUi.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            // Remove selected file
            filesToUpload.remove(index);
            // Update list and ui
            filesToUploadUi.setItems(filesToUpload);
            updateUploadButton();
        }
    }

    private void addFile(File file) {
        filesToUpload.add(file);
        filesToUploadUi.setItems(filesToUpload);
        updateUploadButton();
    }

    private ObservableList<String> getTemplates() {
        return FXCollections.observableArrayList(TemplateController.getAllTemplateNames());
    }

    private void updateUploadButton() {
        if (filesToUpload.size() > 0 && templateType != null) {
            uploadButton.setDisable(false);
        } else {
            uploadButton.setDisable(true);
        }
    }

}
