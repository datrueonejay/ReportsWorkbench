package com.tminions.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;

import com.tminions.app.controllers.OrgUploadTimeController;
import com.tminions.app.models.OrgUploadTimeModel;

public class OrganizationLastUploadedScreen {


    private ObservableList lastUploadTimes;
    @FXML private ListView<OrgUploadTimeModel> lastUploadListView;

    public void initialize() {
        lastUploadTimes = getLastUploadTimes();
        lastUploadListView.setItems(lastUploadTimes);
    }

    private ObservableList<OrgUploadTimeModel> getLastUploadTimes() {
         return FXCollections.observableArrayList(OrgUploadTimeController.getUploadTimes());
    }

}
