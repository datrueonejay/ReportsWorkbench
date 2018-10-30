package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


public class DataUploadController extends BaseController {

    public static HttpResponse<JsonNode> uploadData(String jsonToUpload) {
        HttpResponse<JsonNode> response;
        //then establish connection with server to send data
        try{
            response = Unirest.post(baseUrl + "reports/new-report")
                    .header("Content-Type", "application/json")
                    .body("{"+jsonToUpload+"}")
                    .asJson();
        }catch (Exception e){
            System.out.println("Transmission of json failed");
            return null;
        }
        return response;
    }
}
