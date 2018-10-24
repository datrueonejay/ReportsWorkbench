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

    public void login(LoginModel user, final LoginScreenController screen) {
        HttpResponse<JsonNode> future = null;
		try{
			future = Unirest.post(baseUrl)
			.header("Content-Type", "application/json")
			.body(user)
			.asJson();
		}catch (Exception e){
			screen.failedLogin();
		}
		if (future != null && (future.getStatus() == 200)){
			screen.successLogin();
		}else{
			screen.failedLogin();
		}
	}
}