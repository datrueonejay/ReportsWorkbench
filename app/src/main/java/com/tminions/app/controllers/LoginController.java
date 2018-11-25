package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.tminions.app.Utils.AlertBox;
import com.tminions.app.models.LoginModel;


public class LoginController extends BaseController {

    public static boolean login(LoginModel user) {
        HttpResponse<JsonNode> response;
		try{
			response = Unirest.post(baseUrl + "login/org-user/")
			.header("Content-Type", "application/json")
			.body(user)
			.asJson();
			return response.getStatus() == 200;
		}catch (Exception e){
			AlertBox.display("Error!", "Failed to login, please check your connection.");
			return false;
		}
	}
}
