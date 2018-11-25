package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.tminions.app.models.TemplateColumnsModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TemplateController extends BaseController {

    /**
     * Used to get all template names.
     * @return A list of strings of the template names.
     */
    public static List<String> getAllTemplateNames() {
        HttpResponse<String[]> response;
        //then establish connection with server to send data
        try{
            response = Unirest.get(baseUrl + "templates/all-templates")
                    .header("Content-Type", "application/json")
                    .asObject(String[].class);
            return Arrays.asList(response.getBody());
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    /**
     * Used to get a templateColumnModel, for the given template name.
     * @param templateName The name of the template name to get the templateColumnModel for.
     * @return A new templateColumnModel which has the columns mapped to their datatype, and a list of mandatory columns
     */
    public static TemplateColumnsModel getTemplateColumns(String templateName) {
        HttpResponse<TemplateColumnsModel> response;
        //then establish connection with server to send data
        try{
            response = Unirest.get(baseUrl + "templates/template")
                    .header("Content-Type", "application/json")
                    .header("TEMPLATE_NAME", templateName)
                    .asObject(TemplateColumnsModel.class);
            return response.getBody();
        }catch (Exception e){
            return null;
        }
    }

}
