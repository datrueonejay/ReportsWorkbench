package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.tminions.app.models.ConflictModel;
import com.tminions.app.models.ResolvedConflictModel;

public class ConflictsController extends BaseController {

    /**
     * Gets a conflict from the server
     * @return A conflict if one exists, null otherwise
     */
    public static ConflictModel getConflict() {
        try{
            return Unirest.get(baseUrl + "conflict")
                    .header("Content-Type", "application/json")
                    .asObject(ConflictModel.class).getBody();
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Sends a resolved conflict, with the correct columns
     * @param resolvedConflict A model where conflicts have been resolved
     * @return True on success, false otherwise
     */
    public static boolean resolveConflict(ResolvedConflictModel resolvedConflict) {
        try{
            HttpResponse<JsonNode> response;
            response = Unirest.post(baseUrl + "conflict")
                    .header("Content-Type", "application/json")
                    .body(resolvedConflict)
                    .asJson();
            return response.getStatus() == 200;
        }catch (Exception e){
            return false;
        }
    }

}
