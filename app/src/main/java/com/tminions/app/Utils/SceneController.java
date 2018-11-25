package com.tminions.app.Utils;

import java.io.IOException;

import com.tminions.app.models.LoginModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneController {

	private static SceneController Singleton = null;
	private static Stage stage = null;
	private static LoginModel user = null;
	private static BorderPane root = new BorderPane();
	private static MenuBar navBar = new NavBar().GetNavbar();

	
	public SceneController(Stage primaryStage) throws IOException {
		stage = primaryStage;
        stage.setTitle("TEQ REPORTS WORKBENCH");
		Scene scene = new Scene(root, 600, 600);
		stage.setScene(scene);
        stage.show();
    	switchToScene("loginScreen", false);
        Singleton = this;
	}

	public static SceneController getSceneController() {
		return Singleton;
	}

	public void switchToScene(String sceneName, Boolean displayMenuBar) {
		try {
			Parent content = FXMLLoader.load(getClass().getResource("/fxml/" + sceneName + ".fxml"));
			
			if (displayMenuBar) {
				root.setTop(navBar);
			} else {
				root.setTop(null);
			}

			root.setCenter(content);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setCredentials(LoginModel model){
		this.user = model;
	}

	public String getUsername(){
		return this.user.getUsername();
	}
}
