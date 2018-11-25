package com.tminions.app.screenController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import com.tminions.app.controllers.OrgUploadTimeController;
import com.tminions.app.models.OrgUploadTimeModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrganizationLastUploadedScreen {


    private ObservableList lastUploadTimes;
    @FXML private TableView<OrgUploadTimeModel> lastUploadList;

    public void initialize() {
        lastUploadTimes = getLastUploadTimes();
        lastUploadList.setItems(lastUploadTimes);
    }

    private ObservableList<OrgUploadTimeModel> getLastUploadTimes() {
         return FXCollections.observableArrayList(OrgUploadTimeController.getUploadTimes());
    }

}
