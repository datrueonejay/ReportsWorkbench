package com.tminions.app.controllers;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.tminions.app.models.ReportData;

import java.nio.file.FileSystemNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GenerateReportsController extends BaseController {

    public static ReportData getReportData(String[] columns, String templateName) {
        HttpResponse<JsonNode> response;
        //then establish connection with server to send data
        try{
            response = Unirest.post(baseUrl + "reports/new-report")
                    .header("Content-Type", "application/json")
                    .body("{" + columns + "}")
                    .asJson();

            ReportData reportD = new ReportData(columns, null, templateName);
            return reportD;
        }
        catch (Exception e)
        {
            System.out.println("Transmission of json failed");
            return null;
        }
    }
}

