package com.example._6quiprend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage=stage;
        showStartScene();
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("6 Qui Prend !");
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.show();*/
    }


    public void showStartScene() throws IOException {
        HomePageApplication homePageApplication = new HomePageApplication(stage);
        HomePageController homePageController = new HomePageController(homePageApplication,this);
    }


    public void showAlone() throws IOException {
        AloneApplication aloneApplication =  new AloneApplication(stage);
        AloneController aloneController = new AloneController(aloneApplication,this);
    }

    public static void main(String[] args) {
        launch();
    }
}