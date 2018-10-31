package com.tminions.app.models;

import java.util.List;

public class UploadTimeResponseModel {
    private List<OrgUploadTimeModel> allTimings;

    public UploadTimeResponseModel(List<OrgUploadTimeModel> allTimings) {
        this.allTimings = allTimings;
    }

    public List<OrgUploadTimeModel> getAllTimings() {
        return allTimings;
    }

    public void setAllTimings(List<OrgUploadTimeModel> allTimings) {
        this.allTimings = allTimings;
    }
}
