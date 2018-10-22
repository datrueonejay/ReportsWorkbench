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

    //TODO: integrate controller with server, need to add proper routes and send information properly as either query
    //TODO: or routing params, or as an object for post requests

    public void login(LoginModel user, final LoginScreenController screen) {
        Future<HttpResponse<JsonNode>> future = Unirest.post(baseUrl+"")
                .header("accept", "application/json")
				.body(user)
                .asJsonAsync(new Callback<JsonNode>() {
                            public void failed(UnirestException e) { 
                                System.out.println("Request failed.");
                                System.out.println(e.getMessage());
                            }

                            public void completed(HttpResponse<JsonNode> response) {
                                int code = response.getStatus();
                                JsonNode body = response.getBody();
                                // TODO: Take response and update UI
								if (code == 200){
									screen.successLogin();
								}
								else{
									screen.failedLogin();
								}
                            }

                            public void cancelled() {
                                System.out.println("Request was cancelled");
                            }
                        }

                );
    }
//createUser not needed anymore, commented out incase it needs to be added back in
/**
    public void createUser(LoginModel user) {
        Future<HttpResponse<JsonNode>> future = Unirest.post(baseUrl+"")
                .header("accept", "application/json")
                .field("username", user.getUsername())
                .field("password", user.getPassword())
                .asJsonAsync(new Callback<JsonNode>() {
                                 public void failed(UnirestException e) {
                                     System.out.println("Request failed.");
                                     System.out.println(e.getMessage());
                                 }

                                 public void completed(HttpResponse<JsonNode> response) {
                                     int code = response.getStatus();
                                     JsonNode body = response.getBody();
                                     // TODO: Take response and update UI
                                 }

                                 public void cancelled() {
                                     System.out.println("Request was cancelled");
                                 }
                             }

                );
    }
	**/
}