package com.tminions.app;

import com.tminions.app.Utils.SceneController;
import com.tminions.app.controllers.BaseController;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    Button button;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Init unirest to allow json parsing of objects
        BaseController.initUnirest();
    	new SceneController(primaryStage);
    }

}
