package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.tminions.app.Utils.AlertBox;

import static com.tminions.app.controllers.BaseController.baseUrl;

public class TrendsDataController
{

    public static String getAllTemplateData(String templateName) {
        HttpResponse<JsonNode> response;
        //then establish connection with server to send data

        String jsonSent = "{" + '"' + "template_name" + '"' + ":" + '"' + templateName + '"' + "}";

        try
        {
            response = Unirest.post(baseUrl + "/trends/all-data/")
                    .header("Content-Type", "application/json")
                    .body(jsonSent)
                    .asJson();

            return response.getBody().toString();
        }
        catch (Exception e)
        {
            AlertBox.display("Error!", "Failed to get data for given template, please check your connection.");
            return null;
        }
    }
}