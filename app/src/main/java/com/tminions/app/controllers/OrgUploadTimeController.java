package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.tminions.app.models.OrgUploadTimeModel;
import com.tminions.app.models.UploadTimeResponseModel;

import java.util.Collections;
import java.util.List;

public class OrgUploadTimeController extends BaseController {

    public static List<OrgUploadTimeModel> getUploadTimes() {
        try{
            // Make request
            HttpResponse<UploadTimeResponseModel> res = Unirest.get(baseUrl + "org-upload-time/")
                    .header("Content-Type", "application/json")
                    .asObject(UploadTimeResponseModel.class);
            // Get list of orgs and their upload times from response
            List<OrgUploadTimeModel> orgs = res.getBody().getAllTimings();
            return orgs;
        } catch (Exception e){
            // return empty list if there was an error
            return Collections.emptyList();
        }
    }
}
