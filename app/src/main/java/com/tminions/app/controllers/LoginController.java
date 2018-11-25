package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.tminions.app.models.LoginModel;


public class LoginController extends BaseController {

    public static boolean login(LoginModel user) {
        HttpResponse<JsonNode> response;
		try{
			response = Unirest.post(baseUrl + "login/org-user/")
			.header("Content-Type", "application/json")
			.body(user)
			.asJson();
			if (response.getStatus() == 200) {
				return true;
			} else {
				return false;
			}
		}catch (Exception e){
			return false;
		}
	}
}
