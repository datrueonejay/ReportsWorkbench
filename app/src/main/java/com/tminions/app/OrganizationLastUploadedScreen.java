package com.tminions.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;

public class OrganizationLastUploadedScreen {


    private ObservableList lastUploadTimes = FXCollections.observableArrayList();

    @FXML private ListView<String> lastUploadListView;

    public void initialize() {
        lastUploadTimes = getLastUploadTimes();
        lastUploadListView.setItems(lastUploadTimes);
    }

    private ObservableList<String> getLastUploadTimes() {
        // TODO: Replace with call to controller to get last upload times
        return FXCollections.observableArrayList(
                "ORG 1 " + "JAN 11 1111",
                "ORG 2 " + "DEC 22 2222",
                "ORG 3" + "JUNE 33 3333"
        );
    }

}
