package com.tminions.app.models;

import java.util.ArrayList;
import java.util.List;

public class UploadTimeResponseModel {
    private ArrayList<OrgUploadTimeModel> allTimings;

    public UploadTimeResponseModel() {

    }

    public UploadTimeResponseModel(ArrayList<OrgUploadTimeModel> allTimings) {
        this.allTimings = allTimings;
    }

    public ArrayList<OrgUploadTimeModel> getAllTimings() {
        return allTimings;
    }

    public void setAllTimings(ArrayList<OrgUploadTimeModel> allTimings) {
        this.allTimings = allTimings;
    }
}
