package com.tminions.app.controllers;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.tminions.app.Utils.AlertBox;
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
            AlertBox.display("Error!", "Failed to get data from server, please check your connection.");
            return null;
        }
    }

    public static ReportDataModel getPopulationReportData() {
        HttpResponse<JsonNode> response;
        //then establish connection with server to send data
        try
        {
            response = Unirest.get(baseUrl + "/reports/population-report")
                    .header("Content-Type", "application/json")
                    .asJson();

            String res = response.getBody().toString();
            return JsonMaker.convertJsonResponseToRDM(res);
        }
        catch (Exception e)
        {
            AlertBox.display("Error!", "Failed to get population data from server, please check your connection.");
            return null;
        }
    }
}

