package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TemplateController extends BaseController {

    public static List<String> getAllTemplateNames() {
        HttpResponse<String[]> response;
        //then establish connection with server to send data
        try{
            response = Unirest.get(baseUrl + "templates/all-templates")
                    .header("Content-Type", "application/json")
                    .asObject(String[].class);
            return Arrays.asList(response.getBody());
        }catch (Exception e){
            System.out.println("Transmission of json failed");
            return Collections.emptyList();
        }
    }
}
