package com.tminions.app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	
	private static SceneController Singleton = null;
	private static Stage stage = null;
	
	public SceneController(Stage primaryStage) throws IOException {
		stage = primaryStage;
        stage.setTitle("Team25 Application");
    	switchToScene("loginScreen");
        stage.show();
        Singleton = this; 
	}
	
	public static SceneController getSceneController() {
		return Singleton;
	}
	
	public void switchToScene(String sceneName) {
		try {
			 Parent scene = FXMLLoader.load(getClass().getResource("/fxml/" + sceneName + ".fxml"));
			 stage.setScene(new Scene(scene, 600, 600));
		} catch (Exception e) {
			// TODO Handle this error
		}
	}
	
}
