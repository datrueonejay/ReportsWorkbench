package com.tminions.app;

import com.tminions.app.models.LoginModel;
import org.apache.commons.lang3.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.tminions.app.controllers.LoginController;

public class LoginScreenController {

    @FXML private TextField usernameField;
    @FXML private TextField passField;
    @FXML private Label messageLabel;
    private LoginController loginController = new LoginController();


    public void login() {

        String firstName = usernameField.getText();
        String lastName = passField.getText();
        System.out.println("The credentials used to login were. First Name: " + firstName + " Last Name:" +  lastName);
		//create a login model here passing in first name and last name
		LoginModel loginModel = new LoginModel(firstName, lastName);
		//call logincontroller.(loginModel, this)
		loginController.login(loginModel, this);
    }
	
	public void successLogin(){
		StringBuilder builder = new StringBuilder();
		builder.append("Login Successful!");
		//output login successful
		messageLabel.setText(builder.toString());
		SceneController.getSceneController().switchToScene("dataUploadScreen");        
	}
	
	public void failedLogin() {
		StringBuilder builder = new StringBuilder();
		builder.append("Login Unsuccessful! Your Username or Password is incorrect.");
		//output login unsuccessful
		messageLabel.setText(builder.toString());
	}
}
