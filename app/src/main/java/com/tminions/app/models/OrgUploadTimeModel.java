package com.tminions.app.models;

public class OrgUploadTimeModel {
    private String orgName;
    private String lastUploadTime;

    public  OrgUploadTimeModel() {

    }

    public OrgUploadTimeModel(String orgName, String lastUploadTime) {
        this.orgName = orgName;
        this.lastUploadTime = lastUploadTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLastUploadTime() {
        return lastUploadTime;
    }

    public void setLastUploadTime(String lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
    }

    @Override
    public String toString() {
        return this.orgName + ":    " + this.lastUploadTime;
    }
}
