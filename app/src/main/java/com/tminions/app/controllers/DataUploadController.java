package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


public class DataUploadController extends BaseController {

    public static HttpResponse<JsonNode> uploadData(String userName, String jsonToUpload) {
        HttpResponse<JsonNode> response;
        //then establish connection with server to send data
        try{
            response = Unirest.post(baseUrl + "reports/new-report")
                    .header("Content-Type", "application/json")
				    .header("user-id", userName)
                    .body("{"+jsonToUpload+"}")
                    .asJson();
        }catch (Exception e){
            return null;
        }
        return response;
    }
}
