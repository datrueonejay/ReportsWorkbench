package com.tminions.app;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.tminions.app.models.LoginModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.tminions.app.controllers.LoginController;

public class LoginScreenController {

    @FXML private TextField usernameField;
    @FXML private TextField passField;
    @FXML private Label messageLabel;


    public void login() {

        String firstName = usernameField.getText();
        String lastName = passField.getText();
		//create a login model here passing in first name and last name 
		LoginModel loginModel = new LoginModel(firstName, lastName);
		//call logincontroller.(loginModel, this)
		if (LoginController.login(loginModel)){
			SceneController.getSceneController().setCredentials(loginModel);
			successLogin();
		} else {
			failedLogin();
		}
    }
	
	public void successLogin(){
		StringBuilder builder = new StringBuilder();
		builder.append("Login Successful!");
		//output login successful
		messageLabel.setText(builder.toString());
		SceneController.getSceneController().switchToScene("dataUploadScreen", true);  
	}
	
	public void failedLogin() {
		StringBuilder builder = new StringBuilder();
		builder.append("Login Unsuccessful! Your Username or Password is incorrect.");
		//output login unsuccessful
		messageLabel.setText(builder.toString());
	}
}
