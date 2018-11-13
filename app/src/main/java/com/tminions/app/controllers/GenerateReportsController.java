package com.tminions.app.controllers;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.tminions.app.jsonMaker.JsonMaker;
import com.tminions.app.models.ReportDataModel;

public class GenerateReportsController extends BaseController {

    public static String getReportData(String[] columns, String templateName) {
        String json = "{" + '"'  + "template_name" +  '"' + ":" + '"' + templateName + '"' +
                             "," +  "\"columns\"" + ": [";


        for(int i = 0; i < columns.length; i++)
        {
            if(i < columns.length - 1) json = json + '"' + columns[i] + '"' +  ',';
            else json = json + '"' + columns[i] + '"' + ']';
        }

        json = json + "}";

        System.out.println("The json for the request is: " + json);

        HttpResponse<JsonNode> response;
        //then establish connection with server to send data
        try
        {
            response = Unirest.post(baseUrl + "/reports/get-data/")
                    .header("Content-Type", "application/json")
                    .body(json)
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
