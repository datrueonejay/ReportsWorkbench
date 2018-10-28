package com.tminions.app.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tminions.app.LoginScreenController;

import com.tminions.app.models.LoginModel;

import java.util.concurrent.Future;



public class LoginController extends BaseController {

    public static HttpResponse<JsonNode> login(LoginModel user) {
        HttpResponse<JsonNode> response;
		try{
			response = Unirest.post(baseUrl + "login/org-user/")
			.header("Content-Type", "application/json")
			.body(user)
			.asJson();
		}catch (Exception e){
			return null;
		}
		return response;
	}
}