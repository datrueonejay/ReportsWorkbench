package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import static com.tminions.app.controllers.BaseController.baseUrl;

public class TrendsDataController
{

    public static String getAllTemplateData(String templateName) {
        HttpResponse<JsonNode> response;
        //then establish connection with server to send data

        String jsonSent = "{" + '"' + "template_name" + '"' + ":" + '"' + templateName + '"' + "}";

        System.out.println(jsonSent);

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
            System.out.println("Transmission of json failed");
            return null;
        }
    }
}