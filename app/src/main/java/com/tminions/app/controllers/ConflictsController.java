package com.tminions.app.controllers;

import com.mashape.unirest.http.Unirest;
import com.tminions.app.models.ConflictModel;

public class ConflictsController extends BaseController {

    // Gets and returns one conflict model, returns null if there are no conflicts
    public static ConflictModel getConflict() {
        try{
            return Unirest.get(baseUrl + "conflict")
                    .header("Content-Type", "application/json")
                    .asObject(ConflictModel.class).getBody();
        } catch (Exception e){
            return null;
        }
    }

}
