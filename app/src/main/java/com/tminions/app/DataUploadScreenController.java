package com.tminions.app;

import com.tminions.app.fileParsers.ExcelParser;
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
import org.json.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class DataUploadScreenController {

    private ObservableList filesToUpload = FXCollections.observableArrayList();
    private String templateType;
    private final String BASE_URL = "http://localhost:8000/";
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
                new ExtensionFilter("Excel Files (97 - 2003) .xls", "*.xls"),
                new ExtensionFilter("Excel Files .xlsx", "*.xlsx"),
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
        //TODO: upload json to server
        ArrayList<File> files = new ArrayList<File>(filesToUpload);
        try {
            String json = JsonMaker.jsonFromFiles(files, templateType);
            AlertBox.display("Result", json);
			/**
			//need to stringify json
			JSONParser parser = new JSONParser();
			//convert from JSON string to JSONObject
			JSONObject newJObject = null;
			try {
				newJObject = (JSONObject)parser.parse(json);
			} catch (ParseException e) {
			e.printStackTrace();
			}
			String json2 = newJObject.toString();
			**/
			//then establish connection with server to send data
			HttpResponse<JsonNode> future = null;
			try{
				future = Unirest.post(BASE_URL + "dataupload/something")
				.header("Content-Type", "application/json")
				.body(json)
				.asJson();
			}catch (Exception e){
				System.out.println("Transmission of json failed");
			}
			if (future != null && (future.getStatus() == 200)){
				//TODO: handle server response here
			}else{
				System.out.println("Transmission of json data failed");
			}

        } catch (IOException e) {
            e.printStackTrace();
            // Show popup with error message
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

    // POST request for reports to upload data.
    private void uploadReportData() throws Exception {

        String url = BASE_URL + "/new-report/";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }


    private void addFile(File file) {
        filesToUpload.add(file);
        filesToUploadUi.setItems(filesToUpload);
        updateUploadButton();
    }

    private ObservableList<String> getTemplates() {
        // TODO: Replace with actual code to get templates frm wherever they are stored
        return FXCollections.observableArrayList(
                "Employment Services",
                "Language Services",
                "Other Services"
        );
    }

    private void updateUploadButton() {
        if (filesToUpload.size() > 0 && templateType != null) {
            uploadButton.setDisable(false);
        } else {
            uploadButton.setDisable(true);
        }
    }

}
